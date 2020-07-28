package concurrent;

import java.util.Set;

/**
 * 小区
 *
 * @author iyut
 */
public class CommunityPage {

	private Set<String> links;

	public Set<String> getLinks() {
		return links;
	}

	public CommunityPage(Set<String> links) {
		this.links = links;
	}

}
