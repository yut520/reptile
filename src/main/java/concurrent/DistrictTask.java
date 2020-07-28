package concurrent;

import excel.model.CommunityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 处理行政区任务
 * @author iyut
 */
public class DistrictTask extends RecursiveTask<List<CommunityInfo>> {

	private final District district;

	public DistrictTask(District district) {
		this.district = district;
	}

	@Override
	protected List<CommunityInfo> compute() {
		List<CommunityInfo> result = new ArrayList<>();
		List<RecursiveTask<List<CommunityInfo>>> forks = new ArrayList<>();
		for (District subDistrict : district.getSubDistricts()){
			DistrictTask task = new DistrictTask(subDistrict);
			forks.add(task);
			task.fork();
		}
		for (CommunityPage communityLinks : district.getCommunityPages()){
			CommunityPageTask task = new CommunityPageTask(communityLinks);
			forks.add(task);
			task.fork();
		}
		for (RecursiveTask<List<CommunityInfo>> item : forks){
			result.addAll(item.join());
		}
		return result;
	}
}
