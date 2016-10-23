import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class TaatAnd {
	public static int numberOfComparisions;
	public static int[] docFreq = null;
	public static LinkedList<Integer> postingsListTaatAnd[];
	public static TermIndexReader termReader = new TermIndexReader();

	public static void TaatAnd(String query) throws IOException, CloneNotSupportedException {
		postingsListTaatAnd = null;
		numberOfComparisions = 0;
		LinkedList<Integer> resultOne = new LinkedList<Integer>();
		resultOne = IntersectionOfPostings(query);
		TermIndexReader.bw.write("TaatAnd");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write(query);
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Results: ");
		for (int i = 0; i < resultOne.size(); i++)
			TermIndexReader.bw.write(resultOne.get(i) + " ");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of documents in results: " + resultOne.size());
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of comparisons: " + numberOfComparisions);
		TermIndexReader.bw.newLine();
	}

	public static LinkedList<Integer> IntersectionOfTwo(LinkedList<Integer> postingOne,
			LinkedList<Integer> postingTwo) {

		ListIterator<Integer> pOne = postingOne.listIterator();
		ListIterator<Integer> pTwo = postingTwo.listIterator();
		LinkedList<Integer> result = new LinkedList<Integer>();
		System.out.println("abhilash prrof");
		int i, j;

		for (i = 0, j = 0; i < postingOne.size() && j < postingTwo.size();) {
			int p = postingOne.get(i);
			int q = postingTwo.get(j);

			System.out.println("value of postingOne.get(" + i + ") is " + postingOne.get(i));
			System.out.println("value of postingOTwo.get(" + j + ") is " + postingTwo.get(j));
			System.out.println("values of i and j ar " + i + "and " + j);
			if (p == q) {

				System.out.println("IntersectionOfTwo : Both ar equal ");
				numberOfComparisions++;
				System.out.println("Adding to result" + postingOne.get(i));
				result.add(postingOne.get(i));

				i++;
				j++;

			} else if (p < q) {
				System.out.println("IntersectionOfTwo : p1<p2");
				numberOfComparisions++;
				i++;

			} else {
				System.out.println("IntersectionOfTwo : p1>p2");
				System.out.println("value of postingOne.get(" + i + ") is " + postingOne.get(i));
				System.out.println("value of postingOTwo.get(" + j + ") is " + postingTwo.get(j));
				numberOfComparisions++;
				j++;
			}
		}

		return result;
	}

	public static LinkedList<Integer> IntersectionOfPostings(String query)
			throws IOException, CloneNotSupportedException {
		String[] eachTerm = query.split(" ");

		postingsListTaatAnd = new LinkedList[eachTerm.length];
		docFreq = new int[eachTerm.length];

		for (int i = 0; i < eachTerm.length; i++) {

			postingsListTaatAnd[i] = TermIndexReader.postingsListMain[i];

			docFreq[i] = postingsListTaatAnd[i].size();
		}
		int low = 0;
		int high = docFreq.length - 1;
		LinkedList<Integer> result = new LinkedList<Integer>();
		ListIterator<Integer> resIt = result.listIterator();

		quickSort(docFreq, low, high, postingsListTaatAnd);

		result = postingsListTaatAnd[0];

		for (int io = 1; io < postingsListTaatAnd.length && result.size() != 0; io++) {

			System.out.println("-----------------------------------");
			System.out.println(postingsListTaatAnd.length);

			result = IntersectionOfTwo(result, postingsListTaatAnd[io]);
			System.out.println("result is " + result);
			System.out.println("length is " + postingsListTaatAnd.length);
			System.out.println("postingsListTaatAnd[io] is   " + postingsListTaatAnd[io] + " io is" + io);
		}
		return result;
	}

	public static void quickSort(int[] docFreq, int low, int high, LinkedList<Integer> postingsListArg[]) {
		int mid = (low) + (high - low) / 2;
		int pivot = docFreq[mid];
		int i = low, j = high;
		while (i <= j) {
			while (docFreq[i] < pivot) {
				i++;
			}
			while (docFreq[j] > pivot) {
				j--;
			}
			if (i <= j) {
				int temp = docFreq[i];
				docFreq[i] = docFreq[j];
				docFreq[j] = temp;
				LinkedList<Integer> temp2 = postingsListArg[i];
				postingsListArg[i] = postingsListArg[j];
				postingsListArg[j] = temp2;
				i++;
				j--;
			}
		}
		if (low < j)
			quickSort(docFreq, low, j, postingsListArg);

		if (high > i)
			quickSort(docFreq, i, high, postingsListArg);
	}

}
