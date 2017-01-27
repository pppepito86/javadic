package org.pesho.mydictionary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.pesho.mydictionary.db.WordsCache;

public class MyDictionary extends JFrame {

	private static final String APP_NAME = "My Dictionary";

	private static final long serialVersionUID = 1L;
	
	private JTextField wordTextField;
	private JList<String> wordsList;
	private JTextArea meaningTextArea;
	
	public MyDictionary() {
		setSize(640, 480);
		setTitle(APP_NAME);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(createWordTextField(), c);
		
		c.gridy=1;
		c.weighty = 10;
		add(new JScrollPane(createWordsList()), c);

		c.gridx=1;
		add(new JScrollPane(createMeaningTextArea()), c);
		
		createMenuBar();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}

	private JTextField createWordTextField() {
		wordTextField = new JTextField();
		wordTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void changedUpdate(DocumentEvent e) {
				update();
			}
			
			public void update() {
				String word = MyDictionary.this.wordTextField.getText();
				String[] words = WordsCache.getInstance().getWords();
				for (int i = 0; i < words.length; i++) {
					if (words[i].startsWith(word)) {
						int newSelectedIndex = i;
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								MyDictionary.this.wordsList.setSelectedIndex(newSelectedIndex);
							}
						});
						return;
					}
				}
			}
		});
		return wordTextField;
	}
	
	private JList<String> createWordsList() {
		wordsList = new JList<String>(WordsCache.getInstance().getWords());
		wordsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		wordsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String word = MyDictionary.this.wordsList.getSelectedValue();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MyDictionary.this.showMeaning(word);						
					}
				});
			}
		});
		return wordsList;
	}
	
	public JTextArea createMeaningTextArea() {
		meaningTextArea = new JTextArea();
		meaningTextArea.setEditable(false);
		return meaningTextArea;
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuFile.add(menuItemExit);
		menuBar.add(menuFile);
		
		JMenu menuWord = new JMenu("Word");
		JMenuItem menuItemAdd = new JMenuItem("Modify");
		menuItemAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddWord().setVisible(true);
				dispose();
			}
		});
		menuWord.add(menuItemAdd);
		menuBar.add(menuWord);
		
		JMenu menuTest = new JMenu("Test");
		JMenuItem menuItemTest = new JMenuItem("Test");
		menuItemTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new TestWord().setVisible(true);				
				dispose();
			}
		});
		menuTest.add(menuItemTest);
		menuBar.add(menuTest);
		
		setJMenuBar(menuBar);
	}
	
	private void showMeaning(String word) {
		String meaning = WordsCache.getInstance().getMeaning(word);
		if (meaning == null) return;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyDictionary.this.meaningTextArea.setText(meaning);
				MyDictionary.this.meaningTextArea.setCaretPosition(0);			}
		});
	}
	
}
