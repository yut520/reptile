package excel.model;

import excel.annotation.ExcelField;
import lombok.Data;

@Data
public class CommunityInfo {

	/**
	 * 小区名称
	 */
	private String name;

	/**
	 * 小区均价
	 */
	private String price;

	/**
	 * 建筑年代
	 */
	private String year;

	/**
	 * 建筑类型
	 */
	private String type;

	/**
	 * 物业费用
	 */
	private String propertyFee;

	/**
	 * 物业公司
	 */
	private String propertyCompany;

	/**
	 * 开发商
	 */
	private String developerCompany;

	/**
	 * 楼栋总数
	 */
	private String buildingNum;

	/**
	 * 房屋总数
	 */
	private String roomNum;

	/**
	 * 附近门店
	 */
	private String nearbyStore;

	/**
	 * 链接
	 *
	 * @return
	 */
	private String url;

	@ExcelField(title = "小区名称", align = 2, sort = 10)
	public String getName() {
		return name;
	}

	@ExcelField(title = "小区均价", align = 2, sort = 20)
	public String getPrice() {
		return price;
	}

	@ExcelField(title = "建筑年代", align = 2, sort = 30)
	public String getYear() {
		return year;
	}

	@ExcelField(title = "建筑类型", align = 2, sort = 40)
	public String getType() {
		return type;
	}

	@ExcelField(title = "物业费用", align = 2, sort = 50)
	public String getPropertyFee() {
		return propertyFee;
	}

	@ExcelField(title = "物业公司", align = 2, sort = 60)
	public String getPropertyCompany() {
		return propertyCompany;
	}

	@ExcelField(title = "开发商", align = 2, sort = 70)
	public String getDeveloperCompany() {
		return developerCompany;
	}

	@ExcelField(title = "楼栋总数", align = 2, sort = 80)
	public String getBuildingNum() {
		return buildingNum;
	}

	@ExcelField(title = "房屋总数", align = 2, sort = 90)
	public String getRoomNum() {
		return roomNum;
	}

	@ExcelField(title = "附近门店", align = 2, sort = 100)
	public String getNearbyStore() {
		return nearbyStore;
	}

	@ExcelField(title = "链接地址", align = 2, sort = 110)
	public String getUrl() {
		return url;
	}
}
