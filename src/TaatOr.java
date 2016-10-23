import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class TaatOr {
		public static int numberOfComparisions;
	public static int[] docFreq = null;
	public static LinkedList<Integer> postingsListTaatOr[] = null;

	public static void TaatOr(String query) throws IOException, CloneNotSupportedException {
		numberOfComparisions = 0;
		String[] eachTerm = query.split(",");
		LinkedList<Integer> resultOne = new LinkedList<Integer>();
		resultOne = UnionOfPostings(query);

		TermIndexReader.bw.write("TaatOr");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write(query);
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Results: ");
		for(int i =0; i< resultOne.size(); i++)
		TermIndexReader.bw.write(resultOne.get(i) + " ");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of documents in results: " + resultOne.size());
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of comparisons: " + numberOfComparisions);
		TermIndexReader.bw.newLine();

	}

	public static LinkedList<Integer> UnionOfTwo(LinkedList<Integer> postingOne, LinkedList<Integer> postingTwo) {
		ListIterator<Integer> pOne = postingOne.listIterator();
		ListIterator<Integer> pTwo = postingTwo.listIterator();
		LinkedList<Integer> result = new LinkedList<Integer>();
		int i,j;

		System.out.println("posating list is " +postingOne);
		System.out.println("posating list is " +postingTwo);
		for ( i =0, j=0 ; i<postingOne.size() && j<postingTwo.size() ;  ){
			int p= postingOne.get(i);
			int q= postingTwo.get(j);
			if (p == q) {
				System.out.println("UnionOfTwo : p1==p2");
				numberOfComparisions++;
				result.add(postingOne.get(i));
				i++;
				j++;
			} else if (p < q) {
				System.out.println("UnionOfTwo : p1<p2");
				numberOfComparisions++;		
				result.add(postingOne.get(i));
				i++;
			} else {
				System.out.println("UnionOfTwo : p1>p2");
				numberOfComparisions++;
				result.add(postingTwo.get(j));
				j++;
			}
		}

		if (i == postingOne.size() ) {
			System.out.println("if check enetered i " + i);
			System.out.println("if check enetered  j " + j);
			while (j<postingTwo.size()) {
				result.add(postingTwo.get(j));
				j++;
				pTwo.next();
			}
		} else {
			while (i < postingOne.size()) {
				System.out.println("else check enetered i " + i);
				System.out.println("else check enetered j " + j);
				result.add(postingOne.get(i));
				i++;
			}
		}
		return result;
	}

	public static LinkedList<Integer> UnionOfPostings(String query) throws IOException, CloneNotSupportedException {
		String[] eachTerm = query.split(" ");

		postingsListTaatOr = new LinkedList[eachTerm.length];
		docFreq = new int[eachTerm.length];

		GetPostingsList gPL = new GetPostingsList();
		for (int i = 0; i < eachTerm.length; i++) {
			postingsListTaatOr[i] = TermIndexReader.postingsListMain[i];
			docFreq[i] = postingsListTaatOr[i].size();
		}
		int low = 0;
		int high = docFreq.length - 1;
		LinkedList<Integer> result = new LinkedList<Integer>();
		ListIterator<Integer> resIt = result.listIterator();
		System.out.println("Before Sorting \n");
		for (int ok = 0; ok < postingsListTaatOr.length; ok++)
			System.out.println(postingsListTaatOr[ok]);
		TaatAnd.quickSort(docFreq, low, high, postingsListTaatOr);
		System.out.println("After  Sorting \n");
		for (int ok = 0; ok < postingsListTaatOr.length; ok++)
			System.out.println(postingsListTaatOr[ok]);
		result = postingsListTaatOr[0];

		for (int io = 1; io < postingsListTaatOr.length ; io++) {
			System.out.println("UnionOfPostings: before calling UnionOfTwo");
			result = UnionOfTwo(result, postingsListTaatOr[io]);
			//postingsListTaatOrInter[0] = result;
			TaatAnd.quickSort(docFreq, low, high, postingsListTaatOr);
			System.out.println("UnionOfPostings: result bro" + result);

		}
		return result;
	}
}
