package org.pesho.mydictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pesho.mydictionary.db.WordsCache;

public class Test {
	
	private int wordCount;
	private int current;
	private String[] words;

	public Test(int wordCount) {
		prepareTest(wordCount);
		current = -1;
	}

	private void prepareTest(int wordCount) {
		String[] allWords = WordsCache.getInstance().getWords();
		if (wordCount < 0) wordCount = 1;
		if (wordCount > allWords.length) wordCount = allWords.length;
		this.wordCount = wordCount;
		List<Integer> indexes = new ArrayList<>(allWords.length);
		for (int i = 0; i < allWords.length; i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		words = new String[this.wordCount];
		for (int i = 0; i < this.wordCount; i++) {
			words[i] = allWords[indexes.get(i)];
		}
	}
	
	public String getNextWord() {
		return words[++current];
	}
	
	public boolean isCorrect(String meaning) {
		return WordsCache.getInstance().getMeaning(words[current]).equals(meaning.trim());
	}

	public boolean isFinished() {
		return current >= wordCount - 1;
	}
	
}
