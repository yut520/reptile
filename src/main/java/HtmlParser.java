import com.google.common.util.concurrent.ThreadFactoryBuilder;
import concurrent.ParseCommunityWork;
import excel.ExportExcel;
import excel.model.CommunityInfo;
import lombok.extern.slf4j.Slf4j;
import util.HtmlParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 最初版本 ThreadPool
 * TODO 每个for循环可以优化为ThreadPool
 * 找到更好的fork-join之后就懒得改了
 *
 * @author iyut
 */
@Slf4j
public class HtmlParser {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// 行政区域
		Set<String> districtUrls = HtmlParserUtils.listDistrict("bj");
		Set<String> positionUrls = new HashSet<>();
		for (String url : districtUrls) {
			positionUrls.addAll(HtmlParserUtils.listPosition(url));
		}
		positionUrls = positionUrls.stream().filter(x -> !districtUrls.contains(x)).collect(Collectors.toSet());
		log.info("positionUrls【" + positionUrls.size() + "】：" + positionUrls.toString());
		//小区链接
		Set<String> communitylinks = new HashSet<>();
		for (String url : positionUrls) {
			communitylinks.addAll(HtmlParserUtils.listCommunity(url));
		}
		log.info("communitylinks.size:" + communitylinks.size());
		//组装小区实体
		List<CommunityInfo> communityInfoList = new ArrayList<>();
		List<ParseCommunityWork> communityWorkList = new ArrayList<>();
		for (String url : communitylinks) {
			communityWorkList.add(new ParseCommunityWork(url));
		}
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("community-pool-%d").build();
		ExecutorService pool = new ThreadPoolExecutor(8, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), namedThreadFactory);
		List<Future<CommunityInfo>> futures = pool.invokeAll(communityWorkList);
		pool.shutdown();
		for (Future<CommunityInfo> future : futures) {
			communityInfoList.add(future.get());
		}
		ExportExcel exportExcel = new ExportExcel(null, CommunityInfo.class);
		exportExcel.setDataList(communityInfoList).writeFile("/data/链家小区信息.xlsx");
	}

}
