package org.pesho.mydictionary.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

import org.pesho.mydictionary.exceptions.MyDictionaryException;

public class WordsCache {
	
	private static WordsCache INSTANCE = new WordsCache();
	
	public static WordsCache getInstance() {
		return INSTANCE;
	}
	
	private String[] words;
	private HashMap<String, String> wordsWithMeanings;
	
	private WordsCache() {
		try {
			wordsWithMeanings = DBConnection.getWordsWithMeanings();
			words = DBConnection.getWords();
		} catch (SQLException e) {
			throw new MyDictionaryException("Cannot load cache", e);
		}
	}
	
	public String[] getWords() {
		return words;
	}
	
	public String getMeaning(String word) {
		return wordsWithMeanings.get(word);
	}
	
	public void saveWord(String word, String meaning) throws SQLException {
		if (wordsWithMeanings.containsKey(word)) {
			DBConnection.editWord(word, meaning);
		} else {
			DBConnection.addWord(word, meaning);
		}
		wordsWithMeanings = DBConnection.getWordsWithMeanings();
		words = DBConnection.getWords();
	}
	
	public String searchWord(String word) {
		try {
			String response = sendRequest(word);
			String[] split = response.split("\"");
			if (split.length > 1) return split[1];
		}catch (Exception e) {
		}
		return null;

	}
	
	private String sendRequest(String word) throws Exception {
		String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=bg&dt=t&q="+word;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

}
