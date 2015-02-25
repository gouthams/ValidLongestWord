import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public class ValidLongestWord {
	static String validFirst;
	static String validSecond;

/*  Design : Usage of HashSet vs Ternary Search Tree
 *           Since the constrain was on time a decent HashSet with fewer collision 
 *           can be used. If the space constraint was important Ternary search tree
 *           would have been a better fit. Moreover the ternary search tree has
 *           advantage of both Hashset and the binary search tree. In this
 *           implementation Hashset is used to store the dictionary words.   
*/
	public static void main(String[] args) {
		BufferedReader inputbuffer = null;
		try {
			String currentWord;
			HashSet<String> dictionary = new HashSet<String>();
			// Eliminating space required for storing the valid words
			// ArrayList<String> validList = new ArrayList<String>();

			inputbuffer = new BufferedReader(new FileReader(
					"G:\\Eclipse\\workspace\\Words\\InputWords.txt"));
			while ((currentWord = inputbuffer.readLine()) != null)
				dictionary.add(currentWord);

			// local variables
			String firstWord = "";
			String secondWord = "";
			int validWordCount = 0;

			Iterator<String> iter = dictionary.iterator();
			// Selecting the first and second longest words from the list of valid words identified.
			while (iter.hasNext()) {
				String currentStr = iter.next().toString();
				// Each word in the dictionary is passed to the method
				if (isValidWord(dictionary, currentStr)) {
					// validList.add(currentStr);
					validWordCount++;
					if (firstWord.length() < currentStr.length()) {
						firstWord = currentStr;
					}
					if (currentStr.length() >= secondWord.length()) {
						if (currentStr.length() < firstWord.length())
							secondWord = currentStr;
						else if (!currentStr.equalsIgnoreCase(firstWord)
								&& currentStr.length() == firstWord.length())
							secondWord = currentStr;
					}
				}
			}

			/*
			This comparator was used when the array list of valid words were stored.  
			 
			class lengthComp implements Comparator<String>
			{
				public int compare(String Obj1, String Obj2)
					return -1 * Integer.compare(Obj1.length(), Obj2.length());
			}
		    Collections.sort(validList, new lengthComp());
		
		    System.out.println("1st Longest Word: "+validList.get(0)+" ("+validList.get(0).length()+")\n"+"2nd Longest Word: "+validList.get(1)+" ("+validList.get(1).length()+")\nTotal valid word count: "+validList.size());
		
		   */
		
			// Exposing as static for validation
			validFirst = firstWord;
			validSecond = secondWord;

			System.out.println("1st Longest Word: " + firstWord + " ("
					+ firstWord.length() + ")\n" + "2nd Longest Word: "
					+ secondWord + " (" + secondWord.length()
					+ ")\nTotal valid word count: " + validWordCount);

			// clearing the dictionary after use
			dictionary.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputbuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	// returns true if found a word that is made of other words
	// Each word is split into all possible two word combination.
	// If the first part is valid then the second part is analyzed recursively.
	public static boolean isValidWord(HashSet<String> dictonary, String word) {
		boolean isValid = false;
		for (int i = 1; i < word.length(); i++) {
			// Splitting the word with all possible combination into two words
			String subString1 = word.substring(0, i);
			String subString2 = word.substring(i);

			if (dictonary.contains(subString1)) {
				if (dictonary.contains(subString2)) {
					isValid = true;
					return isValid;
				} else {
					// substring could have valid combination of words
					isValid = isValidWord(dictonary, subString2);

					// Fail fast
					if (isValid)
						return isValid;
				}
			}
		}
		return isValid;
	}
}

/*
 * TestCase1: 
 * Tests for input with equal sized valid words ie 1st and 2nd longest are of same length
 *  
 * TestCase2: 
 * Tests for input with 2nd longest valid word parsed before other valid words parsed and with duplicates
 *  
 * TestCase3:
 * Tests for input with longest valid word parsed before other valid words parsed
 * 
 */
