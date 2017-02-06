package org.pesho.mydictionary.db;

import java.sql.SQLException;
import java.util.HashMap;

import org.pesho.mydictionary.entity.Word;
import org.pesho.mydictionary.exceptions.MyDictionaryException;
import org.pesho.mydictionary.log.Logger;

public class WordsCache {
	
	private static WordsCache INSTANCE = new WordsCache();
	
	public static WordsCache getInstance() {
		return INSTANCE;
	}
	
	private String[] words;
	private HashMap<String, Word> wordsMap;

	
	private WordsCache() {
		try {
			wordsMap = DBConnection.getWordsMap();
			words = DBConnection.getWords();
		} catch (SQLException e) {
			throw new MyDictionaryException("Cannot load cache", e);
		}
	}
	
	public String[] getWords() {
		return words;
	}
	
	public String getMeaning(String word) {
		Word w = wordsMap.get(word);
		if (w == null) {
			return null;
		}
		return w.getMeaning();
	}
	
	public void saveWord(String word, String meaning) throws SQLException {
		if (wordsMap.containsKey(word)) {
			if (meaning.length() != 0) {
				DBConnection.editWord(word, meaning);
				wordsMap.get(word).setMeaning(meaning);
			} else {
				DBConnection.deleteWord(word);
				wordsMap.remove(word);
				words = DBConnection.getWords();
			}
		} else if (meaning.length() != 0) {
			DBConnection.addWord(word, meaning);
			wordsMap = DBConnection.getWordsMap();
			words = DBConnection.getWords();
		} else {
			throw new MyDictionaryException("Meaning should not be empty");
		}
	}
	
	public boolean addTestResult(String word, boolean success) {
		try {
			DBConnection.addTestResult(wordsMap.get(word).getId(), success);
			return true;
		} catch (SQLException e) {
			Logger.error(e);
			return false;
		}
	}
	
}
