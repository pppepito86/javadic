package org.pesho.mydictionary.entity;

public class Word {
	
	private int id;
	private String word;
	private String meaning;
	
	public Word(int id, String word, String meaning) {
		this.id = id;
		this.word = word;
		this.meaning = meaning;
	}
	
	public int getId() {
		return id;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getMeaning() {
		return meaning;
	}
	
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

}
