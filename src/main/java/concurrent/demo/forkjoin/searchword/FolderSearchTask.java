package concurrent.demo.forkjoin.searchword;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSearchTask extends RecursiveTask<Long> {

	private final Folder folder;

	private final String searchWord;

	public FolderSearchTask(Folder folder, String searchWord) {
		super();
		this.folder = folder;
		this.searchWord = searchWord;
	}

	@Override
	protected Long compute() {
		long count = 0L;
		List<RecursiveTask<Long>> folderSearchTasks = new LinkedList<>();
		for (Folder subFolder : folder.getSubFolders()){
			FolderSearchTask task = new FolderSearchTask(subFolder, searchWord);
			folderSearchTasks.add(task);
			task.fork();
		}
		for (Document doc : folder.getDocuments()){
			DocumentSearchTask task = new DocumentSearchTask(doc, searchWord);
			folderSearchTasks.add(task);
			task.fork();
		}
		for (RecursiveTask<Long> task : folderSearchTasks){
			count += task.join();
		}
		return count;
	}
}
