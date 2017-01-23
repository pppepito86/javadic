package org.pesho.mydictionary;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.pesho.mydictionary.db.DBConnection;

public class MyDictionary extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MyDictionary() {
		setSize(800, 600);
		setPreferredSize(new Dimension(800, 600));
		setTitle("My Dictionary");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTextField word = new JTextField();
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0; c.gridy = 0;
		c.weightx = 1; c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(word, c);
		JList<String> list = new JList<>();
		DefaultListModel<String> model = new DefaultListModel<>();
		String[] words = DBConnection.getWords();
		for (String w: words) {
			model.addElement(w);
		}
		list.setModel(model);
		c.gridy=1;
		c.weighty = 10;
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, c);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("file");
		menuFile.add(new JMenuItem("close"));
		menuBar.add(menuFile);
		
		JMenu menuWord = new JMenu("word");
		JMenuItem menuItemAdd = new JMenuItem("add");
		menuItemAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddWord addWord = new AddWord();
				addWord.setVisible(true);
			}
		});
		menuWord.add(menuItemAdd);
		menuWord.add(new JMenuItem("delete"));
		menuBar.add(menuWord);
		
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	
	
	

}
