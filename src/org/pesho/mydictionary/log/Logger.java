package org.pesho.mydictionary.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
	
	private static File LOG_FILE = new File("mydictionary.log");
	
	public static String getLogFilePath() {
		return LOG_FILE.getAbsolutePath();
	}
	
	public static synchronized void error(String message) {
		try (FileWriter fileWriter = new FileWriter(LOG_FILE, true)) {
			fileWriter.append(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void error(Throwable t) {
		try (PrintWriter printWriter = new PrintWriter(new FileWriter(LOG_FILE, true))) {
			printWriter.append(t.getMessage() + "\n");
			t.printStackTrace(printWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
