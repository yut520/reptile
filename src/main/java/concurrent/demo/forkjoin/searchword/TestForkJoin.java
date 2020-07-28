package concurrent.demo.forkjoin.searchword;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class TestForkJoin {

	private final ForkJoinPool forkJoinPool = new ForkJoinPool();

	Long countOccurrencesInParallel(Folder folder, String searchedWord) {
		return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
	}

	public static void main(String[] args) throws IOException {
		TestForkJoin testForkJoin = new TestForkJoin();
		Folder folder = Folder.fromDir(new File("/data/logs/qms"));
		System.out.println(testForkJoin.countOccurrencesInParallel(folder, "Job"));
	}
}
