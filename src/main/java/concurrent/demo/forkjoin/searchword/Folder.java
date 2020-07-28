package concurrent.demo.forkjoin.searchword;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Folder {

	private List<Folder> subFolders;

	private List<Document> documents;

	public Folder(List<Folder> subFolders, List<Document> documents) {
		this.subFolders = subFolders;
		this.documents = documents;
	}

	public List<Folder> getSubFolders() {
		return subFolders;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	static Folder fromDir(File dir) throws IOException {
		List<Folder> subFolders = new LinkedList<>();
		List<Document> documents = new LinkedList<>();
		log.info("遍历目录===" + dir.getPath());
		for (File file : Objects.requireNonNull(dir.listFiles())) {
			if (file.isDirectory()) {
				subFolders.add(Folder.fromDir(file));
			} else {
				documents.add(Document.fromFile(file));
			}
		}
		return new Folder(subFolders, documents);
	}
}
