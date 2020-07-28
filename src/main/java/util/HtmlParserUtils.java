package util;

import com.alibaba.fastjson.JSONObject;
import excel.model.CommunityInfo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 网页请求与解析工具
 *
 * @author iyut
 */
@Slf4j
public class HtmlParserUtils {

	/**
	 * 根据城市获取行政区域
	 *
	 * @param city bj
	 * @return
	 * @throws IOException
	 */
	public static Set<String> listDistrict(String city) throws IOException {
		log.info("开始===" + System.currentTimeMillis());
		String url = "https://" + city + ".lianjia.com/xiaoqu";
		Set<String> result = new HashSet<>();
		log.info("发送请求===" + System.currentTimeMillis());
		Document doc = Jsoup
				.connect(url)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
				.get();
		log.info("请求返回===" + System.currentTimeMillis());
		List<String> links = doc.select("div[data-role=ershoufang] > div > a").eachAttr("href");
		log.info("获取href元素===" + System.currentTimeMillis());
		for (String link : links) {
			if (link.startsWith("/xiaoqu")) {
				result.add("https://bj.lianjia.com" + link);
			} else {
				result.add(link);
			}
		}
		log.info("结束===" + System.currentTimeMillis());
		return result;
	}

	/**
	 * 根据行政区域获取位置
	 *
	 * @param url bj
	 * @return
	 * @throws IOException
	 */
	public static Set<String> listPosition(String url) throws IOException {
		Set<String> result = new HashSet<>();
		log.info("获取具体区域" + url);
		Document doc = Jsoup
				.connect(url)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
				.get();

		List<String> links = doc.select("div[data-role=ershoufang] > div").get(1).select("a").eachAttr("href");
		String domain = url.substring(0, 22);
		for (String link : links) {
			result.add(domain + link);
		}
		log.info("下一级区域【" + result.size() + "】：" + result.toString());
		return result;
	}

	/**
	 * 获取小区链接
	 *
	 * @return
	 * @throws IOException
	 */
	public static Set<String> listCommunity(String districtUrl) throws IOException {
		Set<String> result = new HashSet<>();
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
			result.addAll(doc.select("div.title > a").eachAttr("href"));
			log.info("总页数：" + total + "==当前页：" + i);
			if (total <= i) {
				return result;
			}
		}
	}

	/**
	 * 组装小区信息
	 *
	 * @param url url
	 * @return
	 * @throws IOException
	 */
	public static CommunityInfo getCommunity(String url) throws IOException {
		log.info("url:" + url);
		CommunityInfo communityInfo = new CommunityInfo();
		communityInfo.setUrl(url);
		Document communityDoc = Jsoup
				.connect(url)
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.header("Accept-Language", "zh-CN,zh;q=0.8")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
				.get();
		Elements detailTitle = communityDoc.select("h1.detailTitle");
		Elements unitPrice = communityDoc.select("span.xiaoquUnitPrice");
		Elements communityItems = communityDoc.select("div.xiaoquInfoItem");
		try {
			if (!CollectionUtils.isEmpty(detailTitle)) {
				communityInfo.setName(detailTitle.get(0).text());
			}
			if (!CollectionUtils.isEmpty(unitPrice)) {
				communityInfo.setPrice(unitPrice.get(0).text());
			}
			for (Element communityInfoItem : communityItems) {
				if ("建筑年代".equals(communityInfoItem.child(0).text())) {
					communityInfo.setYear(communityInfoItem.child(1).text());
				}
				if ("建筑类型".equals(communityInfoItem.child(0).text())) {
					communityInfo.setType(communityInfoItem.child(1).text());
				}
				if ("物业费用".equals(communityInfoItem.child(0).text())) {
					communityInfo.setPropertyFee(communityInfoItem.child(1).text());
				}
				if ("物业公司".equals(communityInfoItem.child(0).text())) {
					communityInfo.setPropertyCompany(communityInfoItem.child(1).text());
				}
				if ("开发商".equals(communityInfoItem.child(0).text())) {
					communityInfo.setDeveloperCompany(communityInfoItem.child(1).text());
				}
				if ("楼栋总数".equals(communityInfoItem.child(0).text())) {
					communityInfo.setBuildingNum(communityInfoItem.child(1).text());
				}
				if ("房屋总数".equals(communityInfoItem.child(0).text())) {
					communityInfo.setRoomNum(communityInfoItem.child(1).text());
				}
				if ("附近门店".equals(communityInfoItem.child(0).text())) {
					communityInfo.setNearbyStore(communityInfoItem.child(1).text());
				}
			}
		} catch (Exception e) {
			log.info("获取小区信息失败：" + url);
		}
		log.info("communityInfo:" + communityInfo.toString());
		return communityInfo;
	}

}
