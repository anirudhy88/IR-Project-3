import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.plaf.synth.SynthStyle;

public class DaatAnd {
	public static int[] docFreq = null;
	public static int numberOfComparisions;
	public static LinkedList<Integer> postingsListDaatAnd[] = null;

	public static void DaatAnd(String query) throws IOException, CloneNotSupportedException {
		numberOfComparisions = 0;

		LinkedList<Integer> resultOne = new LinkedList<Integer>();
		resultOne = IntersectionOfPostings(query);
		System.out.println("The result in datt and case is " + resultOne);
		TermIndexReader.bw.write("DaatAnd");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write(query);
		TermIndexReader.bw.newLine();
		for (int i = 0; i < resultOne.size(); i++)
			TermIndexReader.bw.write(resultOne.get(i) + " ");
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of documents in results: " + resultOne.size());
		TermIndexReader.bw.newLine();
		TermIndexReader.bw.write("Number of comparisons: " + numberOfComparisions);
		TermIndexReader.bw.newLine();

	}

	public static int GetMaxElement(int inputArray[]) {
		int maxDocId = 0;
		for (int i = 0; i < inputArray.length; i++) {
			if (maxDocId < inputArray[i]) {
				maxDocId = inputArray[i];
				numberOfComparisions++;
			}
		}
		return maxDocId;
	}

	public static LinkedList<Integer> IntersectionOfPostings(String query)
			throws IOException, CloneNotSupportedException {

		String[] eachTerm = query.split(" ");

		int[] intArray = new int[eachTerm.length];
		int[] pointerArray = new int[eachTerm.length];
		int maxDocId = 0;

		postingsListDaatAnd = new LinkedList[eachTerm.length];

		docFreq = new int[eachTerm.length];

		for (int i = 0; i < eachTerm.length; i++) {
			postingsListDaatAnd[i] = TermIndexReader.postingsListMain[i];
			docFreq[i] = postingsListDaatAnd[i].size();
		}
		if (eachTerm.length == 1) {
			return postingsListDaatAnd[0];
		}
		// System.out.println(intArray[0]);
		int low = 0;
		int high = docFreq.length - 1;
		LinkedList<Integer> result = new LinkedList<Integer>();
		ListIterator<Integer> resIt = result.listIterator();

		TaatAnd.quickSort(docFreq, low, high, postingsListDaatAnd);

		for (int j = 0; j < postingsListDaatAnd.length; j++) {
			pointerArray[j] = 0;
		}

		Boolean exit = false;
		int sizeToCheck = 0;
		while (!exit) {
			Boolean cont = false;
			System.out.println("Main for loop and lowFreq is " + exit);

			// Now populate the intArray with the docId's of each term.
			for (int i = 0; i < postingsListDaatAnd.length; i++) {

				if (pointerArray[i] == (postingsListDaatAnd[i].size())) {
					exit = true;
				}
				if (exit)
					break;
				intArray[i] = postingsListDaatAnd[i].get(pointerArray[i]);
				System.out.println("Populating intArray with i as  " + i + " and " + intArray[i]);
			}
			if (exit)
				break;

			maxDocId = GetMaxElement(intArray);
			System.out.println("Value of maxDocId is " + maxDocId);
			int track = 0;
			for (int i = 0; i < pointerArray.length; i++)
				System.out.println("The int Array is : " + intArray[i]);
			for (int i = 1; i < pointerArray.length; i++) {

				if (intArray[i - 1] == intArray[i]) {
					// numberOfComparisions++;
					track++;
					System.out.println("Entered ths equals check");
				}

			}
			System.out.println("value of track is " + track);
			// System.out.println("value of pointerArray length is " +
			// pointerArray.length);
			if (track == pointerArray.length - 1)
				cont = true;

			if (cont) {
				System.out.println("Populating resulttttttttttttttttttttttt  " + pointerArray[0]);
				System.out.println("Populating resulttttttttttttttttttttttt  " + intArray[0]);
				result.add(postingsListDaatAnd[0].get(pointerArray[0]));
				System.out.println("result is  chek enet" + result);
				for (int i = 0; i < pointerArray.length; i++) {
					// if(pointerArray[i] < (postingsListDaatAnd[i].size()-1)) {
					// System.out.println("Incremented pointerArray["+i+"]");
					pointerArray[i] = pointerArray[i] + 1;
					// }
				}
			} else {
				for (int i = 0; i < pointerArray.length; i++) {
					System.out.println("value of i is " + i);
					if (intArray[i] < maxDocId) {
						// numberOfComparisions++;
						System.out.println("intArray[" + i + "] <= maxDocId - true");
						pointerArray[i] = pointerArray[i] + 1;
					}
				}
			}
		}
		return result;
	}
}
