import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.plaf.PopupMenuUI;

public class DaatOr {

	public static int[] docFreq = null;
	public static int numberOfComparisions;
	public static LinkedList<Integer> postingsListDaatOr[] = null;

	public static void DaatOr(String query) throws IOException, CloneNotSupportedException {
		numberOfComparisions = 0;

		LinkedList<Integer> resultOne = new LinkedList<Integer>();
		resultOne = UnionOfPostings(query);
		System.out.println("The result in datt or case is " + resultOne);
		TermIndexReader.bw.write("DaatOr");
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

	public static LinkedList<Integer> UnionOfPostings(String query) throws IOException, CloneNotSupportedException {
		String[] eachTerm = query.split(" ");
		LinkedList<Integer> intArray = new LinkedList<Integer>();
		int[] pointerArray = new int[eachTerm.length];
		int maxDocId = 0;

		postingsListDaatOr = new LinkedList[eachTerm.length];
		docFreq = new int[eachTerm.length];

		for (int i = 0; i < eachTerm.length; i++) {
			postingsListDaatOr[i] = TermIndexReader.postingsListMain[i];
			docFreq[i] = postingsListDaatOr[i].size();
		}
		
		if (eachTerm.length == 1) {
			return postingsListDaatOr[0];
		}
		
		// System.out.println(intArray[0]);
		int low = 0;
		int high = docFreq.length - 1;
		LinkedList<Integer> result = new LinkedList<Integer>();
		ListIterator<Integer> resIt = result.listIterator();

		TaatAnd.quickSort(docFreq, low, high, postingsListDaatOr);

		for (int j = 0; j < postingsListDaatOr.length; j++) {
			pointerArray[j] = 0;
		}

		Boolean exit = false;
		int sizeToCheck = 0;

		int allSizeReached = 0;
		while (!exit) {

			int jDash = 0;
			Boolean cont = false;
			
			System.out.println("Main for loop and lowFreq is " + exit);
			System.out.println("vale of postingsListDaatOr.length is " + postingsListDaatOr.length);
			intArray.clear();
			if (allSizeReached >= postingsListDaatOr.length-1) {
				 break;
			}
			
			// Now populate the intArray with the docId's of each term.
			for (int i = 0; i < postingsListDaatOr.length; i++) {

				if (pointerArray[i] >= (postingsListDaatOr[i].size())) {
					System.out.println("continuing");
					intArray.add(Integer.MAX_VALUE);
					//allSizeReached++;
					continue;
				}
				System.out.println("value of jDash is " + jDash);
				intArray.add(postingsListDaatOr[i].get(pointerArray[i]));

			}
			if(intArray.size() == 1) {
				result.add(intArray.get(0));
				break;
			}
			// Finding smallest number and it's indexes from the intArray
			LinkedList<Integer> minIndex = new LinkedList<Integer>();
			for (int i = 0; i < minIndex.size(); i++)
				System.out.println("The indexs are " + minIndex.get(i));
			int index = 0;
			int min = intArray.get(index);
			for (int i = 0; i < intArray.size(); i++) {
				if (intArray.get(i) < min) {
					min = intArray.get(i);
				}
			}

			System.out.println("value of min is " + min);
			for (int i = 0; i < intArray.size(); i++) {
				if (intArray.get(i) == min) {
					System.out.println("value of intArray[" + i + "] is " + intArray.get(i));
					minIndex.add(i);
				}
			}
			
			
			int ind = 0;
			for (int i = 0; i < minIndex.size(); i++) {
				System.out.println("The indexs are " + minIndex.get(i));
				if (ind == 0)
					result.add(postingsListDaatOr[minIndex.get(i)].get(pointerArray[minIndex.get(i)]));
				ind++;
				//postingsListDaatOr[minIndex.get(i)].remove(pointerArray[minIndex.get(i)]);
				pointerArray[minIndex.get(i)] = pointerArray[minIndex.get(i)] + 1;
				if(pointerArray[minIndex.get(i)] >= postingsListDaatOr[minIndex.get(i)].size()) {
					allSizeReached++;
				}
			}
			for (int i = 0; i < result.size(); i++)
				System.out.println("result is " + result.get(i));	
		}
		for(int i=0; i<pointerArray.length ; i++){
			if(pointerArray[i] < postingsListDaatOr[i].size()) {
				while(pointerArray[i] < postingsListDaatOr[i].size()){
					result.add(postingsListDaatOr[i].get(pointerArray[i]));
					pointerArray[i] = pointerArray[i] + 1;
				}
			}
		}
		return result;
	}

}
/*
 * //maxDocId = GetMaxElement(intArray);
 * System.out.println("Value of maxDocId is " + maxDocId); int track = 0; for
 * (int i = 0; i < pointerArray.length; i++)
 * System.out.println("The int Array is : " + intArray[i]); for (int i = 1; i <
 * pointerArray.length; i++) {
 * 
 * if (intArray[i - 1] == intArray[i]) { // numberOfComparisions++; track++;
 * System.out.println("Entered ths equals check"); }
 * 
 * } System.out.println("value of track is " + track); //
 * System.out.println("value of pointerArray length is " + //
 * pointerArray.length); if (track == pointerArray.length - 1) cont = true;
 * 
 * if (cont) { System.out.println("Populating resulttttttttttttttttttttttt  " +
 * pointerArray[0]);
 * System.out.println("Populating resulttttttttttttttttttttttt  " +
 * intArray[0]); result.add(postingsListDaatOr[0].get(pointerArray[0]));
 * System.out.println("result is  chek enet" + result); for (int i = 0; i <
 * pointerArray.length; i++) { // if(pointerArray[i] <
 * (postingsListDaatAnd[i].size()-1)) { //
 * System.out.println("Incremented pointerArray["+i+"]"); pointerArray[i] =
 * pointerArray[i] + 1; // } } } else { for (int i = 0; i < pointerArray.length;
 * i++) { System.out.println("value of i is " + i); if (intArray[i] < maxDocId)
 * { System.out.println("Adding as alll are not euql :" + intArray[i]);
 * result.add(intArray[i]); // numberOfComparisions++;
 * System.out.println("intArray[" + i + "] <= maxDocId - true"); pointerArray[i]
 * = pointerArray[i] + 1; } } } }
 */
