package org.pesho.mydictionary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.pesho.mydictionary.entity.Word;
import org.pesho.mydictionary.exceptions.MyDictionaryException;

public class DBConnection {

	private static final String WORDS_TABLE_NAME = "words";
	private static final String ID_COLUMN_NAME = "id";
	private static final String MEANING_COLUMN_NAME = "meaning";
	private static final String WORD_COLUMN_NAME = "word";

	private static final String TESTS_TABLE_NAME = "tests";
	private static final String WORD_ID_COLUMN_NAME = "word_id";
	private static final String SUCCESS_COLUMN_NAME = "success";

	public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost/mydictionary?characterEncoding=UTF-8";

	public static void testConnection() {
		try {
			Class.forName(MYSQL_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new MyDictionaryException("Cannot load database driver: " + MYSQL_DRIVER);
		}
		try (Connection connection = getConnection()) {
		} catch (SQLException e) {
			// ignore on close
		}
	}

	private static Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL, "root", "password");
		} catch (Exception e) {
			throw new MyDictionaryException("Cannot get database connection", e);
		}
	}

	public static void addWord(String word, String meaning) throws SQLException {
		String query = String.format("insert into %s (%s, %s) values (?, ?)", WORDS_TABLE_NAME, WORD_COLUMN_NAME,
				MEANING_COLUMN_NAME);
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, word);
			ps.setString(2, meaning);
			ps.execute();
		}
	}

	public static void editWord(String word, String meaning) throws SQLException {
		String query = String.format("update %s set %s = ? where %s = ?", WORDS_TABLE_NAME, MEANING_COLUMN_NAME,
				WORD_COLUMN_NAME);
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, meaning);
			ps.setString(2, word);
			ps.execute();
		}
	}

	public static String[] getWords() throws SQLException {
		String query = String.format("select %s from %s", WORD_COLUMN_NAME, WORDS_TABLE_NAME);
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			int size = 0;
			if (rs.last()) {
				size = rs.getRow();
				rs.beforeFirst();
			}
			String[] words = new String[size];
			for (int i = 0; i < size; i++) {
				rs.next();
				words[i] = rs.getString(WORD_COLUMN_NAME);
			}
			return words;
		}
	}

	public static HashMap<String, Word> getWordsMap() throws SQLException {
		String query = String.format("select %s, %s, %s from %s", ID_COLUMN_NAME, WORD_COLUMN_NAME, MEANING_COLUMN_NAME,
				WORDS_TABLE_NAME);
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(query);
				ResultSet rs = ps.executeQuery();) {
			HashMap<String, Word> words = new HashMap<>();
			while (rs.next()) {
				Word word = new Word(rs.getInt(ID_COLUMN_NAME), rs.getString(WORD_COLUMN_NAME), rs.getString(MEANING_COLUMN_NAME));
				words.put(word.getWord(), word);
			}
			return words;
		}
	}

	public static void deleteWord(String word) throws SQLException {
		String query = String.format("delete from %s where %s = ?", WORDS_TABLE_NAME, WORD_COLUMN_NAME);
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, word);
			ps.execute();
		}
	}

	public static void addTestResult(int id, boolean success) throws SQLException {
		String query = String.format("insert into %s (%s, %s) values (?, ?)", TESTS_TABLE_NAME, WORD_ID_COLUMN_NAME, SUCCESS_COLUMN_NAME);
		try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.setBoolean(2, success);
			ps.execute();
		}
	}

}
