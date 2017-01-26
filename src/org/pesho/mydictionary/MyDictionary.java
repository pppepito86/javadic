package org.pesho.mydictionary;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

import org.pesho.mydictionary.db.DBConnection;
import org.pesho.mydictionary.db.WordsCache;

public class MyDictionary extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField word;
	private JList<String> list;
	private JTextArea meaning;
	
	public MyDictionary() {
		setSize(640, 480);
		setTitle("My Dictionary");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		word = new JTextField();
		
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
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
			
			public void update() {
				System.out.println("change");
				String word = MyDictionary.this.word.getText();
				String[] words = WordsCache.getInstance().getWords();
				for (int i = 0; i < words.length; i++) {
					System.out.println("www" + words[i] + " " +word);
					if (words[i].startsWith(word)) {
						System.out.println("index " + i);
						//MyDictionary.this.list.setSelectedIndex(i);
						//MyDictionary.this.list.getSelectionModel().setLeadSelectionIndex(i);
						MyDictionary.this.list.setSelectedIndex(i);
						return;
					}
				}
			}
		});
		list = new JList<>(DBConnection.getWords());
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		c.gridy=1;
		c.weighty = 10;
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, c);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						String word = MyDictionary.this.list.getSelectedValue();
						if (word.equals(MyDictionary.this.word.getText())) return;
//						MyDictionary.this.word.setText(word);
						MyDictionary.this.showMeaning(word);						
					}
				});
			}
		});
		
		c.gridx=1;
		meaning = new JTextArea();
		JScrollPane scroll2 = new JScrollPane(meaning);
		add(scroll2, c);
		
		createMenuBar();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
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
		
		JMenu menuWord = new JMenu("word");
		JMenuItem menuItemAdd = new JMenuItem("add");
		menuItemAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddWord addWord = new AddWord();
				addWord.setVisible(true);
			}
		});
		menuWord.add(menuItemAdd);
		menuWord.add(new JMenuItem("delete"));
		menuBar.add(menuWord);
		
		setJMenuBar(menuBar);
	}
	
	private void showMeaning(String word) {
		String meaning = WordsCache.getInstance().getMeaning(word);
		System.out.println("meaning is " + meaning);
		if (meaning == null) return;

		this.meaning.setText(meaning);
	}
	
	
	
}
