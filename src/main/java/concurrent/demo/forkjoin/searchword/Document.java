package concurrent.demo.forkjoin.searchword;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Document {

	private List<String> lines;

	public Document(List<String> lines){
		this.lines = lines;
	}

	List<String> getLines(){
		return lines;
	}

	static Document fromFile(File file) throws IOException {
		List<String> lines = new LinkedList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line = reader.readLine();
			while (null != line){
				lines.add(line);
				line = reader.readLine();
			}
			return new Document(lines);
		}
	}
}
