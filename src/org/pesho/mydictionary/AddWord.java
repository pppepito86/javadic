package org.pesho.mydictionary;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.pesho.mydictionary.db.DBConnection;
import org.pesho.mydictionary.db.WordsCache;

public class AddWord extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField word = new JTextField();
	private JTextArea meaning = new JTextArea();
	private JButton save = new JButton("Add");

	public AddWord() {
		setSize(400, 200);
		setLayout(new GridLayout(3, 1));
		add(word);
		word.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			private void update() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						String word = AddWord.this.word.getText();
						String meaning = WordsCache.getInstance().getMeaning(word);
						if (meaning != null) {
							AddWord.this.save.setText("Edit");
						} else {
							AddWord.this.save.setText("Add");
							meaning = WordsCache.getInstance().searchWord(word);
						}
						if (meaning != null) {
							AddWord.this.meaning.setText(meaning);
						}
					}
				});
			}
		});
		add(meaning);
		add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = AddWord.this.word.getText().trim();
				String meaning = AddWord.this.meaning.getText().trim();
				if (word.length() == 0 || meaning.length() == 0)
					return;
				WordsCache.getInstance().saveWord(word, meaning);
				dispose();
				new MyDictionary().setVisible(true);
			}
		});
	}

}
