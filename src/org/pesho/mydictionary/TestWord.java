package org.pesho.mydictionary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.pesho.mydictionary.db.WordsCache;

public class TestWord extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField wordsCount = new JTextField();
	private JButton start = new JButton("Start");
	private JButton exit = new JButton("Exit");
	private JLabel correctLabel = new JLabel("Correct");
	private JLabel wrongLabel = new JLabel("Wrong");
	private JTextField correct = new JTextField("0");
	private JTextField wrong = new JTextField("0");

	private JTextField word = new JTextField();
	private JTextArea meaning = new JTextArea();
	private JButton save = new JButton("Add");

	public TestWord() {
		setSize(400, 200);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(new JLabel("Number of words:"), c);
		
		c.gridy = 1;
		add(wordsCount, c);
		
		c.gridx = 1;
		add(start, c);
		
		c.gridx = 2;
		add(exit, c);
		
		c.gridx =3; c.gridy=0;
		add(new JLabel("Correct:"), c);

		c.gridy = 1;
		add(correct, c);
		
		c.gridx = 4; c.gridy=0;
		add(new JLabel("Wrong:"), c);

		c.gridy = 1;
		add(wrong, c);
		
		c.gridx = 0; c.gridy=2;
		add(new JLabel("Word to translate:"), c);

		c.gridx=3;
		
	}
	
}
