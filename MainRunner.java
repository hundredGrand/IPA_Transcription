// MainRunner.java
// takes in a text file of words next to their IPA transcription and creates a Treemap to map
// them together. Then, uses user input of English words to make a new text file displaying
// IPA transcriiptions.
// Last Edited: 5/22/2024
// Gwen Goetz

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.TreeMap;

public class MainRunner {
	private static final String IN_FILE_NAME = "IPA_Source.txt";
	private static final String OUT_FILE_NAME = "IPA_Transcription.txt";

	public static void main (String [] args) throws FileNotFoundException {
		TreeMap lexicon = addDictionary(IN_FILE_NAME);
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Welcome to IPA decoder!\nPlease enter the word(s) you would like " +
				"to be decoded: ");
		String input = keyboard.nextLine().toLowerCase();

		String result = getResult(input, lexicon);
		printToFile(result);
	}

	/**
	 * AddDictionary
	 * @param filename name of input file
	 * @return Treemap of input words mapped to their ipa transcription
	 * @throws FileNotFoundException
	 */
	public static TreeMap addDictionary (String filename) throws FileNotFoundException {
		File f = new File(filename);
		if (!f.canRead()) {
			throw new FileNotFoundException();
		} else {
			TreeMap<String, String> lexicon = new TreeMap<>();

			Scanner infile = new Scanner(f);
			while (infile.hasNext()) {
				String[] line = infile.nextLine().split(",");
				lexicon.put(line[0].toLowerCase(), line[1]);
			}
			return lexicon;
		}
	}

	/**
	 * getResult
	 * @param input String of original words to be decoded to IPA
	 * @param lexicon Treemap mapping words to their IPA transcription
	 * @return String result of IPA transcription
	 */
	public static String getResult(String input, TreeMap<String, String> lexicon) {
		String[] words = input.split(" ");
		String result = "";

		for (int i = 0; i < words.length; ++i) {
			if (lexicon.containsKey(words[i])) {
				result += lexicon.get(words[i]) + " ";
			} else {
				return "Sorry, our database doesn't include the word \"" + words[i] + "\".";
			}
		}
		return result;
	}

	/**
	 * openOutputFile
	 * pre: result of IPA decoding has been produced
	 * post: file have been opened and output result printed in file
	 * @param result -- String of decoded text
	 * @throws FileNotFoundException
	 */
	private static void printToFile (String result) throws FileNotFoundException {
		File f2 = new File(OUT_FILE_NAME);
		PrintStream outfile = new PrintStream(f2);
		outfile.println(result);
	}
}
