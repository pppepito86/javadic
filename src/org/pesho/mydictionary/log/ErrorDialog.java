package org.pesho.mydictionary.log;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorDialog {
	
	public static void show(Throwable t) {
		show(null, t);
	}
	
	public static void show(Frame parent, Throwable t) {
		String errorMessage = "Error occured <"+t.getMessage()+">\n"
				+ "Check log file for more details:\n" + Logger.getLogFilePath();
		if (parent == null) {
			parent = new JFrame();
		}
		JOptionPane.showMessageDialog(parent, errorMessage);
	}

}
