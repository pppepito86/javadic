package org.pesho.mydictionary;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.pesho.mydictionary.db.DBConnection;

public class AddWord extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField word = new JTextField();
	private JTextField meaning = new JTextField();
	private JButton save = new JButton("Save");
	
	public AddWord() {
		setSize(200, 200);
		setLayout(new GridLayout(3, 1));
		add(word);
		add(meaning);
		add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = AddWord.this.word.getText().trim();
				String meaning = AddWord.this.meaning.getText().trim();
				if (word.length() == 0 || meaning.length() == 0) return;
				DBConnection.addWord(word, meaning);
				dispose();
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
}
