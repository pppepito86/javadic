package org.pesho.mydictionary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	
	public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost/mydictionary";
		
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
			String query = "insert into words (word, meaning) values (?, ?)";
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, word);
			ps.setString(2, meaning);
			ps.execute();
			connection.close();
		} catch (Exception e) {
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
			return new String[]{};
		}
	}
	
}
