package concurrent.demo.forkjoin.searchword;

import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Long> {

	private final Document document;

	private final String searchWord;

	public DocumentSearchTask(Document document, String searchWord) {
		super();
		this.document = document;
		this.searchWord = searchWord;
	}

	@Override
	protected Long compute() {
		return WorkCounter.occurrencesCount(document, searchWord);
	}
}
