package org.pesho.mydictionary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class DBConnection {
	
	public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost/mydictionary?characterEncoding=UTF-8";
		
	public static void testConnection() {
		try {
			Class.forName(MYSQL_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, "root", "password");
			connection.close();
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}
	
	private static Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL, "root", "password");
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}
	
	public static void addWord(String word, String meaning) {
		try {
			System.out.println(word + " " + meaning);
			String query = "insert into words (word, meaning) values (?, ?)";
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, word);
			ps.setString(2, meaning);
			ps.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void editWord(String word, String meaning) {
		try {
			String query = "update words set meaning = ? where word = ?";
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, meaning);
			ps.setString(2, word);
			ps.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getWords() {
		try {
			String query = "select word from words";
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			int size = 0;
			if (rs.last()) {
				size = rs.getRow();
				rs.beforeFirst();
			}
			String[] words = new String[size];
			for (int i = 0; i < size; i++) {
				rs.next();
				words[i] = rs.getString("word");
			}
			return words;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[]{};
		}
	}
	
	public static HashMap<String, String> getWordsWithMeanings() {
		HashMap<String, String> words = new HashMap<>();
		try {
			String query = "select word, meaning from words";
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			int size = 0;
			if (rs.last()) {
				size = rs.getRow();
				rs.beforeFirst();
			}
			for (int i = 0; i < size; i++) {
				rs.next();
				words.put(rs.getString("word"), rs.getString("meaning"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return words;
	}
	
}
