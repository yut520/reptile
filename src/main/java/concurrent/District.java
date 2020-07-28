package concurrent;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 行政区
 * @author iyut
 */
@Slf4j
public class District {

	/**
	 * 子级行政区
	 */
	private List<District> subDistricts;

	/**
	 * 当前行政区下的小区
	 */
	private List<CommunityPage> communityPages;

	public List<District> getSubDistricts() {
		return subDistricts;
	}

	public List<CommunityPage> getCommunityPages() {
		return communityPages;
	}

	public District(List<District> subDistricts, List<CommunityPage> communityPages) {
		this.subDistricts = subDistricts;
		this.communityPages = communityPages;
	}

	static District fromDistrictUrl(String districtUrl) throws IOException {
		List<District> subDistricts = new ArrayList<>();
		List<CommunityPage> communityPages = new ArrayList<>();
		Integer total = 0;
		for (int i = 1; ; i++) {
			String url = districtUrl + "pg" + i;
			log.info("小区链接：" + url);
			Document doc = Jsoup
					.connect(url)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
					.get();
			// 获取总页数
			if (total == 0) {
				JSONObject pageInfo = JSONObject.parseObject(doc.select("div[page-data]").attr("page-data"));
				if (null != pageInfo && pageInfo.size() > 0) {
					total = (Integer) pageInfo.get("totalPage");
				}
			}
			if (total == 30) {
				List<String> links = new ArrayList<>();
				Elements elements = doc.select("div[data-role=ershoufang] > div");
				// 顶级行政区域
				if (null != elements && elements.size() == 1) {
					links.addAll(doc.select("div[data-role=ershoufang] > div > a").eachAttr("href"));
				} else if (null != elements && elements.size() == 2){
					// 子行政区
					links.addAll(doc.select("div[data-role=ershoufang] > div").get(1).select("a").eachAttr("href"));
				}
				// https://bj.lianjia.com
				String domain = url.substring(0, 22);
				for (String link : links) {
					String childDistrictUrl = domain + link;
					// 有域名就不用拼接域名了
					if (link.startsWith("https")) {
						childDistrictUrl = link;
					}
					// 排除父级url
					if (districtUrl.equals(childDistrictUrl)) {
						log.info("父级地址【" + districtUrl + "】==" + "子级地址【" + childDistrictUrl + "】");
						continue;
					}
					subDistricts.add(fromDistrictUrl(childDistrictUrl));
				}
				break;
			}
			List<String> communitys = doc.select("div.title > a").eachAttr("href");
			if (!CollectionUtils.isEmpty(communitys)) {
				communityPages.add(new CommunityPage(new HashSet<>(communitys)));
			}
			log.info("总页数：" + total + "==当前页：" + i);
			if (total <= i) {
				break;
			}
		}
		return new District(subDistricts, communityPages);
	}
}
