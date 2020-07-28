package concurrent.demo.crawler;

public interface LinkHandler {

	/**
	 * 添加链接
	 * @param link
	 */
	void addVisited(String link);

	/**
	 * 校验链接是否已经浏览过
	 * @param link
	 * @return
	 */
	boolean visited(String link);

	/**
	 * 链接数量
	 * @return
	 */
	int size();

}
