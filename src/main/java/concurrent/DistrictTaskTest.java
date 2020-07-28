package concurrent;

import excel.ExportExcel;
import excel.model.CommunityInfo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * 链家小区信息任务入口
 * @author iyut
 */
public class DistrictTaskTest {

	private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

	static List<CommunityInfo> listCommunity(String url) throws IOException {
		return FORK_JOIN_POOL.invoke(new DistrictTask(District.fromDistrictUrl(url)));
	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		List<CommunityInfo> result = listCommunity("https://bj.lianjia.com/xiaoqu/");
		System.out.println(result.size());
		ExportExcel exportExcel = new ExportExcel(null, CommunityInfo.class);
		exportExcel.setDataList(result).writeFile("/data/链家区信息.xlsx");
		System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000);
	}

}
