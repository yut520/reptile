package concurrent.demo.crawler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class WebCrawler6 implements LinkHandler {

	private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<>());

	@Override
	public void addVisited(String link) {

	}

	@Override
	public boolean visited(String link) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}
}
