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
import org.pesho.mydictionary.translate.GoogleTranslator;

public class ModifyWord extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField wordTextField;
	private JTextArea meaningTextArea = new JTextArea();
	private JLabel infoLabel = new JLabel("");
	private JButton saveButton;

	public ModifyWord() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				new MyDictionary().setVisible(true);
				dispose();
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
		add(createWordTextField(), c);

		c.gridy = 1;
		c.gridx = 1;
		c.weighty = 0.1;
		add(infoLabel, c);
		c.gridy = 2;
		c.gridx = 0;
		add(new JLabel("Meaning:"), c);
		c.gridx = 1;
		c.weighty = 5;
		c.weightx = 4;
		meaningTextArea.setFont(wordTextField.getFont().deriveFont(20F));
		add(meaningTextArea, c);

		c.gridy = 3;
		c.gridx = 0;
		c.weighty = 0.5;
		c.weightx = 1;
		c.gridwidth=3;
		c.insets = new Insets(10, 80, 10, 80);
		add(createSaveButton(), c);

	}
	
	private JTextField createWordTextField() {
		wordTextField = new JTextField();
		wordTextField.setFont(wordTextField.getFont().deriveFont(22F));
		wordTextField.getDocument().addDocumentListener(new DocumentListener() {
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
						String word = ModifyWord.this.wordTextField.getText();
						if (word.length() == 0) {
							infoLabel.setText(" ");
							meaningTextArea.setText("");
							return;
						}
						String meaning = WordsCache.getInstance().getMeaning(word);
						if (meaning != null) {
							infoLabel.setText("* edit/delete");
						} else {
							meaning = GoogleTranslator.translate(word);
							infoLabel.setText("* add");
						}
						if (meaning != null) {
							ModifyWord.this.meaningTextArea.setText(meaning);
						}
					}
				});
			}
		});
		return wordTextField;
	}
	
	private JButton createSaveButton() {
		saveButton = new JButton("Modify");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = ModifyWord.this.wordTextField.getText().trim();
				String meaning = ModifyWord.this.meaningTextArea.getText().trim();
				if (word.length() == 0) {
					ErrorDialog.show("Word should not be empty");
					return;
				}
				try {
					WordsCache.getInstance().saveWord(word, meaning);
				} catch (SQLException exc) {
					ErrorDialog.show(exc);
				}
				dispose();
				new MyDictionary().setVisible(true);
			}
		});
		return saveButton;
	}

}
