import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TermIndexReader implements Cloneable {

	public static String[] eachTerm;

	public static BufferedReader br = null;
	public static BufferedWriter bw = null;
	public HashMap<String, LinkedList<Integer>> invertedIndexMap = new HashMap<String, LinkedList<Integer>>();
	public static LinkedList<Integer> postingsListMain[];
	public LinkedList<Integer> postingsListTaatAnd[];

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		postingsListMain = null;
		String INDEX_DIR = "D:\\535\\ProjectTwo\\index";
    	int docId = 0;
		int count = 0;
		int fieldCount = 0;
		Directory indexDir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexReader indexReader = DirectoryReader.open(indexDir);
		int numDocs = indexReader.numDocs();
		System.out.println("Number of documents :" + numDocs);
		Fields fields = MultiFields.getFields(indexReader);
		TermIndexReader tr = new TermIndexReader();
		for (String field : fields) {
			if (field.contains("text")) {
				fieldCount++;
				Terms terms = fields.terms(field);
				int fieldDocCount = indexReader.getDocCount(field);
				TermsEnum termsEnum = terms.iterator();
				PostingsEnum myEnum = null;
				while ((termsEnum.next()) != null) {
					LinkedList<Integer> postingsListMain = new LinkedList<Integer>();
					myEnum = termsEnum.postings(myEnum);
					while (myEnum.nextDoc() != PostingsEnum.NO_MORE_DOCS) {
						postingsListMain.add(myEnum.docID());
					}
					tr.invertedIndexMap.put(termsEnum.term().utf8ToString(), postingsListMain);
				}
			}
		}
		
		br = new BufferedReader(new FileReader("D:\\535\\ProjectTwo\\TestFiles\\input.txt"));
		File file = new File("D:\\535\\ProjectTwo\\TestFiles\\output.txt");
		
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		HashMap<String, LinkedList<Integer>> invertedIndexMap2 = (HashMap<String, LinkedList<Integer>>) tr.invertedIndexMap
				.clone();
		String sCurrentLine;
		GetPostingsList gPLThree = new GetPostingsList();
		while ((sCurrentLine = br.readLine()) != null) {
			String[] eachTerm = sCurrentLine.split(" ");
			postingsListMain = new LinkedList[eachTerm.length];
			for (int i = 0; i < eachTerm.length; i++) {
				postingsListMain[i] = gPLThree.GetPostingsList(eachTerm[i], invertedIndexMap2);
			}
			LinkedList<Integer> posTaatAnd[] = (LinkedList<Integer>[]) postingsListMain.clone();
			LinkedList<Integer> posTaatOr[] = (LinkedList<Integer>[]) postingsListMain.clone();
			TaatAnd.TaatAnd(sCurrentLine);
			TaatOr.TaatOr(sCurrentLine);
			DaatAnd.DaatAnd(sCurrentLine);
			DaatOr.DaatOr(sCurrentLine);
		}
		bw.close();
	}


}
