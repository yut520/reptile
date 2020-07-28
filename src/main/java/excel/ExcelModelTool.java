package excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 */
public class ExcelModelTool {

	private static Logger log = LoggerFactory.getLogger(ExcelModelTool.class);

	private boolean is_xls = false;

	private boolean error_flag = false;

	/**
	 * 工作薄对象
	 */
	private Workbook wb;

	private int sheetIndex;
	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 标题行号
	 */
	private int headerNum;


	/**
	 * 图片信息 // map等待存储excel图片
	 */
	private Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();


	public Map<String, PictureData> getSheetIndexPicMap() {
		return sheetIndexPicMap;
	}

	/**
	 * 构造函数
	 *
	 * @param fileName  导入文件，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelModelTool(String fileName, int headerNum)
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}

	/**
	 * 构造函数
	 *
	 * @param file      导入文件对象，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelModelTool(File file, int headerNum)
			throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param fileName   导入文件
	 * @param headerNum  标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelModelTool(String fileName, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 *
	 * @param file       导入文件对象
	 * @param headerNum  标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelModelTool(File file, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 *
	 * @param fileName   导入文件对象
	 * @param headerNum  标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ExcelModelTool(String fileName, InputStream is, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)) {
			throw new RuntimeException("导入文档为空!");
		} else if (fileName.toLowerCase().endsWith("xls")) {
			is_xls = true;
			this.wb = new HSSFWorkbook(is);
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			is_xls = false;
			this.wb = new XSSFWorkbook(is);
		} else {
			throw new RuntimeException("文档格式不正确!");
		}
		if (this.wb.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheetIndex = sheetIndex;
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;

		initTheSheetPic();
		log.debug("Initialize success.");
	}

	private void initTheSheetPic() {


		// 判断用07还是03的方法获取图片
		if (is_xls) {
			sheetIndexPicMap = getSheetPictrues03(sheetIndex, (HSSFSheet) sheet, (HSSFWorkbook) wb);
		} else {
			sheetIndexPicMap = getSheetPictrues07(sheetIndex, (XSSFSheet) sheet, (XSSFWorkbook) wb);
		}


	}

	/**
	 * 获取行对象(从0开始)
	 *
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum) {
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取第一个数据行号(从0开始)
	 *
	 * @return
	 */
	public int getDataRowNum() {
		return headerNum + 1;
	}

	/**
	 * 获取最后一个数据行号(从0开始)
	 *
	 * @return
	 */
	public int getLastDataRowNum() {
		return this.sheet.getLastRowNum();
	}

	/**
	 * 获取最后一个列号(从0开始)，用标题行计算的
	 *
	 * @return
	 */
	public int getLastCellNum() {
		return this.getRow(headerNum).getLastCellNum() - 1;
	}

	/**
	 * 获取某一行得最后一个列号(从0开始)
	 *
	 * @return
	 */
	public int getLastCellNumByRow(int rowNum) {
		Row row = this.getRow(rowNum);
		return (row == null) ? -1 : (row.getLastCellNum() - 1);
	}

	/**
	 * 获取单元格值
	 *
	 * @param rowNum 获取的行
	 * @param column 获取单元格列号,从0开始
	 * @return 单元格值
	 */

	public String getCellValue(int rowNum, int column) {
		Row row = this.getRow(rowNum);
		return this.getCellValue(row, column);
	}

	public String getCellValue(Row row, int column) {

		Cell cell = row.getCell(column);
		String temp = "";
		if (cell == null) {
			log.info("cell value [" + row.getRowNum() + "," + column + "] 为空");
		} else {
			int cellType = cell.getCellType();
			switch (cellType) {
				case Cell.CELL_TYPE_STRING:
					temp = cell.getStringCellValue().trim();
					temp = StringUtils.isEmpty(temp) ? "" : temp;
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					temp = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					temp = String.valueOf(cell.getCellFormula().trim());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						temp = dateFormat.format(date);
					} else {
						temp = new DecimalFormat("#.######").format(cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BLANK:
					temp = "";
					break;
				case Cell.CELL_TYPE_ERROR:
					temp = "ERROR";
					break;
				default:
					temp = cell.toString().trim();
					break;
			}
		}

		return temp;
	}

	/**
	 * 设置单元格值
	 *
	 * @param rowNum 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	public boolean setCellValue(int rowNum, int column, Object val) {
		Row row = this.getRow(rowNum);
		return setCellValue(row, column, val);
	}

	public boolean setCellValue(Row row, int column, Object val) {
		try {
			Cell cell = row.getCell(column);
			if (cell == null) {
				cell = row.createCell(column);

			}
			if (val == null) {
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				cell.setCellValue((Date) val);
			}
			return true;
		} catch (Exception ex) {
			log.error("Set cell value [" + row.getRowNum() + "," + column + "] error", ex);

		}
		return false;
	}

	/**
	 * 删除指定的行
	 *
	 * @param row
	 */
	public void delRow(int row) {
		if (row > 0) {
			try {
//                sheet.shiftRows(row, sheet.getLastRowNum(), -1);
				Row dtRow = this.getRow(row);
				sheet.removeRow(dtRow);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} else {
			log.info("row号{}错误", row);
		}
	}

	/**
	 * 获取Excel2003图片
	 *
	 * @param sheetNum 当前sheet编号
	 * @param sheet    当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 * @throws IOException
	 */
	private Map<String, PictureData> getSheetPictrues03(int sheetNum,
														HSSFSheet sheet, HSSFWorkbook workbook) {

		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
		List<HSSFPictureData> pictures = workbook.getAllPictures();
		if (pictures.size() != 0) {
			HSSFPatriarch drawingPatriarch = sheet.getDrawingPatriarch();
			if (drawingPatriarch != null) {
				for (HSSFShape shape : drawingPatriarch.getChildren()) {
					HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
					if (shape instanceof HSSFPicture) {
						HSSFPicture pic = (HSSFPicture) shape;
						int pictureIndex = pic.getPictureIndex() - 1;
						HSSFPictureData picData = pictures.get(pictureIndex);
						String picIndex = String.valueOf(anchor.getRow1()) + "_"
								+ String.valueOf(anchor.getCol1());
						System.out.println(picIndex);
						sheetIndexPicMap.put(picIndex, picData);
					}
				}
			}

			return sheetIndexPicMap;
		} else {
			return null;
		}
	}

	/**
	 * 获取Excel2007图片
	 *
	 * @param sheetNum 当前sheet编号
	 * @param sheet    当前sheet对象
	 * @param workbook 工作簿对象
	 * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
	 */
	private Map<String, PictureData> getSheetPictrues07(int sheetNum,
														XSSFSheet sheet, XSSFWorkbook workbook) {
		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();

		for (POIXMLDocumentPart dr : sheet.getRelations()) {
			if (dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture pic = (XSSFPicture) shape;
					XSSFClientAnchor anchor = pic.getPreferredSize();
					CTMarker ctMarker = anchor.getFrom();
					String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
					sheetIndexPicMap.put(picIndex, pic.getPictureData());
				}
			}
		}

		return sheetIndexPicMap;
	}


	/**
	 * 输出数据流
	 *
	 * @param os 输出数据流
	 */
	public ExcelModelTool write(OutputStream os) throws IOException {
		wb.write(os);
		return this;
	}

	/**
	 * 输出到文件
	 *
	 * @param name 输出文件名
	 */
	public ExcelModelTool writeFile(String name) throws FileNotFoundException, IOException {
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}


	public boolean getErrorFlag() {
		return error_flag;
	}

	public void setErrorFlag(boolean error_flag) {
		this.error_flag = error_flag;
	}


}
