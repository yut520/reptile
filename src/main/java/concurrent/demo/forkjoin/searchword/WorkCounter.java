package concurrent.demo.forkjoin.searchword;

import concurrent.demo.forkjoin.searchword.Document;

public class WorkCounter {

	static String[] wordeIn(String line){
		return line.trim().split("(\\s|\\p{Punct})+");
	}

	static Long occurrencesCount(Document document, String searchWord){
		long count = 0;
		for (String line : document.getLines()){
			for (String word : wordeIn(line)){
				if (word.equals(searchWord)){
					count ++;
				}
			}
		}
		return count;
	}
}
