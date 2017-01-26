package org.pesho.mydictionary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	private JTextField meaning = new JTextField();
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
		wordsCount.setText(WordsCache.getInstance().getWords().length+"");
		add(wordsCount, c);
		
		c.gridx = 1;
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startTest();
			}
		});
		add(start, c);
		
		c.gridx = 2;
		add(exit, c);
		
		c.gridx =3; c.gridy=0;
		add(new JLabel("Correct:"), c);

		c.gridy = 1;
		correct.setEditable(false);
		add(correct, c);
		
		c.gridx = 4; c.gridy=0;
		add(new JLabel("Wrong:"), c);

		c.gridy = 1;
		wrong.setEditable(false);
		add(wrong, c);
		
		c.gridx = 0; c.gridy=2;
		add(new JLabel("Word to translate:"), c);

		c.gridx=2; c.gridwidth=3;
		meaning.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					checkWord();
					setWord();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		word.setEditable(false);
		add(word, c);
		
		c.gridx = 0; c.gridy=3;
		add(new JLabel("Meaning:"), c);

		c.gridx=2;
		add(meaning, c);
	}
	
	private void startTest() {
		wordsCount.setEditable(false);
		test = new Test(Integer.parseInt(wordsCount.getText()));
		setWord();
	}
	
	private void checkWord() {
		String m = meaning.getText();
		if (test.isCorrect(m)) {
			increase(correct);
		} else {
			increase(wrong);
		}
	}
	
	private void increase(JTextField result) {
		int current = Integer.parseInt(result.getText());
		result.setText(String.valueOf(current+1));
	}

	private void setWord() {
		meaning.setText("");
		if (!test.isFinished()) {
			word.setText(test.getNextWord());
		} else {
			word.setText("");
			word.setEditable(false);
		}
	}
	
	private Test test;
}
