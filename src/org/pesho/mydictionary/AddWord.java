package org.pesho.mydictionary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.pesho.mydictionary.db.WordsCache;
import org.pesho.mydictionary.log.ErrorDialog;

public class AddWord extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField word = new JTextField();
	private JTextArea meaning = new JTextArea();
	private JLabel info = new JLabel("");
	private JButton save = new JButton("Add");

	public AddWord() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new MyDictionary().setVisible(true);
				super.windowClosing(e);
			}
		});
		setSize(400, 400);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(new JLabel("Word:"), c);
		c.gridx = 1;
		add(word, c);
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
						if (word.length() == 0) {
							info.setText(" ");
							meaning.setText("");
							return;
						}
						String meaning = WordsCache.getInstance().getMeaning(word);
						if (meaning != null) {
							info.setText("* edit");
							AddWord.this.save.setText("Edit");
						} else {
							AddWord.this.save.setText("Add");
							meaning = WordsCache.getInstance().searchWord(word);
							info.setText("* add");
						}
						if (meaning != null) {
							AddWord.this.meaning.setText(meaning);
						}
					}
				});
			}
		});
		c.gridy = 1;
		c.gridx = 1;
		c.weighty = 0.1;
		add(info, c);
		c.gridy = 2;
		c.gridx = 0;
		add(new JLabel("Meaning:"), c);
		c.gridx = 1;
		c.weighty = 5;
		c.weightx = 4;
		add(meaning, c);

		c.gridy = 3;
		c.gridx = 0;
		c.weighty = 0.5;
		c.weightx = 1;
		c.gridwidth=3;
		c.insets = new Insets(10, 80, 10, 80);
		add(save, c);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = AddWord.this.word.getText().trim();
				String meaning = AddWord.this.meaning.getText().trim();
				if (word.length() == 0 || meaning.length() == 0)
					return;
				try {
					WordsCache.getInstance().saveWord(word, meaning);
				} catch (SQLException exc) {
					ErrorDialog.show(exc);
				}
				dispose();
				new MyDictionary().setVisible(true);
			}
		});
	}

}
