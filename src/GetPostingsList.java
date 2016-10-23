import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.lucene.index.Term;

public class GetPostingsList {
	public  LinkedList<Integer> GetPostingsList(String query, HashMap<String, LinkedList<Integer>> invertedIndexMap2) throws IOException, CloneNotSupportedException {
			TermIndexReader.bw.write("GetPostings");
			TermIndexReader.bw.newLine();
			TermIndexReader.bw.write(query);
			TermIndexReader.bw.newLine();
			LinkedList<Integer> pos = invertedIndexMap2.get(query);
			TermIndexReader.bw.write("Postings list: ");
			for( int i=0; i< pos.size() ; i++) {
			TermIndexReader.bw.write(pos.get(i) +" ");
			}
			TermIndexReader.bw.newLine();			
			return pos;	
	}
}
