package com.muhammadHijazi.project1.mailHandler;

import java.util.LinkedList;

/*
 * Basic Keyword finder,
 * needs alot of works, its really more of a 
 * word counter then anything else 
 * 2014 Muhammad Hijazi
 */
public class KeywordFinder {

	public static class find {
		public static String[] findKeywords(String input, int numOfKeywords) {
			String[] found = new String[numOfKeywords];
			String[] split;
			LinkedList<Keyword> keyWords = new LinkedList<Keyword>();
			// removes punctuation
			String search = input.replaceAll("\\p{Punct}", "");
			// removes any words smaller then 3 letters
			search = search.replaceAll("\\b[\\S]{1,3}\\b", "");
			// removes carrige return
			search = search.replaceAll("\\n", " ");
			search = search.replaceAll("\\r", " ");
			// Removes extra spaces
			search = search.replaceAll("\\s{2,}", " ");
			// convers all letters to lowercase
			search = search.toLowerCase();
			// split the string into an array
			split = search.split(" ");

			/*
			 * checks to see if a word has already been added to the keyword
			 * list, if so it incriments the number of occurences, otherwise the
			 * word is added to the list.
			 * 
			 * If a word is incrimented, its position is resorted.
			 */
			for (int i = 0; i < split.length; i++) {
				Keyword next = new Keyword(split[i]);
				if (keyWords.size() == 0)
					keyWords.add(next);
				else {
					for (int k = 0; k < keyWords.size(); k++) {
						if (keyWords.get(k).getWord().equals(split[i])) {
							int index = k;
							keyWords.get(index).incFrequency();
							for (int j = 0; j < index; j++) {
								if (keyWords.get(index).getFrequency() > keyWords
										.get(j).getFrequency()) {
									next = keyWords.get(index);
									keyWords.remove(index);
									keyWords.add(j, next);
									break;
								}
							}
							break;
						} else if (k == keyWords.size() - 1) {
							keyWords.add(next);
							break;
						}
					}

				}
			}
			if (!keyWords.isEmpty()) {
				if (keyWords.size() < numOfKeywords) {
					numOfKeywords = keyWords.size();
					found = new String[numOfKeywords];
				}
				for (int i = 0; i < numOfKeywords; i++) {
					found[i] = keyWords.get(i).getWord();
				}
			}
			return found;
		}
	}
}

class Keyword {
	String keyword;
	int frequency;

	public Keyword(String word) {
		keyword = word;
		frequency = 1;
	}

	public void incFrequency() {
		frequency++;
	}

	public int getFrequency() {
		return frequency;
	}

	public String getWord() {
		return keyword;
	}
}