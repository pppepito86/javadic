package org.pesho.mydictionary.log;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorDialog {
	
	public static void show(String cause) {
		String errorMessage = "Error occured <"+cause+">";
		show(null, errorMessage);
	}

	public static void show(Throwable t) {
		show(null, t);
	}
	
	public static void show(Frame parent, Throwable t) {
		String errorMessage = "Error occured <"+t.getMessage()+">\n"
				+ "Check log file for more details:\n" + Logger.getLogFilePath();
		show(parent, errorMessage);
	}
	
	public static void show(Frame parent, String errorMessage) {
		if (parent == null) {
			parent = new JFrame();
		}
		JOptionPane.showMessageDialog(parent, errorMessage);
	}

}
