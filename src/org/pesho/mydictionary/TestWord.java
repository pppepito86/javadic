package org.pesho.mydictionary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.pesho.mydictionary.db.WordsCache;

public class TestWord extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField wordsCount = new JTextField();
	private JButton start = new JButton("Start");
	private JButton exit = new JButton("Exit");
	private JTextField correct = new JTextField("0");
	private JTextField wrong = new JTextField("0");

	private JTextField word = new JTextField();
	private JTextField meaning = new JTextField();

	public TestWord() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				new MyDictionary().setVisible(true);
				dispose();
			}
		});
		setSize(400, 200);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		clear();
		c.insets = new Insets(5, 5, 5, 5);
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
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MyDictionary().setVisible(true);
				dispose();
			}
		});
		add(exit, c);
		
		c.gridx =3; c.gridy=0;
		add(new JLabel("Correct"), c);

		c.gridy = 1;
		correct.setEditable(false);
		add(correct, c);
		
		c.gridx = 4; c.gridy=0;
		add(new JLabel("Wrong"), c);

		c.gridy = 1;
		wrong.setEditable(false);
		add(wrong, c);

		c.weighty=2; c.gridwidth=2;
		c.gridx = 0; c.gridy=2;
		add(new JLabel("Word to translate:"), c);

		c.gridx=2; c.gridwidth=3;
		meaning.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || (test.isCorrect(meaning.getText()))) {
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
		
		c.gridx = 0; c.gridy=3; c.gridwidth=2;
		add(new JLabel("Meaning:"), c);

		word.setFont(word.getFont().deriveFont(20F));
		meaning.setFont(meaning.getFont().deriveFont(20F));

		c.gridx=2; c.gridwidth=3;
		meaning.setEditable(false);
		add(meaning, c);
	}
	
	private void startTest() {
		start.setEnabled(false);
		wordsCount.setEditable(false);
		test = new Test(Integer.parseInt(wordsCount.getText()));
		wordsCount.setText(String.valueOf(test.getWordsCount()));
		meaning.setEditable(true);
		correct.setText("0");
		wrong.setText("0");
		setWord();
	}
	
	private void checkWord() {
		String m = meaning.getText();
		if (test.isCorrect(m, true)) {
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
			clear();
		}
	}
	
	private void clear() {
		word.setText("");
		word.setEditable(false);
		meaning.setText("");
		meaning.setEditable(false);
		wordsCount.setText(WordsCache.getInstance().getWords().length+"");
		wordsCount.setEditable(true);
		start.setEnabled(true);
	}
	
	private Test test;
}
