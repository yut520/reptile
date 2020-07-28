package concurrent;

import excel.model.CommunityInfo;
import util.HtmlParserUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 将小区链接转化为小区信息任务
 *
 * @author iyut
 */
public class CommunityPageTask extends RecursiveTask<List<CommunityInfo>> {

	private final CommunityPage communityPage;

	public CommunityPageTask(CommunityPage communityPage) {
		this.communityPage = communityPage;
	}

	@Override
	protected List<CommunityInfo> compute() {
		List<CommunityInfo> result = new ArrayList<>();
		for (String link : communityPage.getLinks()) {
			try {
				result.add(HtmlParserUtils.getCommunity(link));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
