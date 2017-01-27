package org.pesho.mydictionary.exceptions;

public class MyDictionaryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MyDictionaryException() {
		super();
	}

	public MyDictionaryException(String cause) {
		super(cause);
	}
	
	public MyDictionaryException(Throwable t) {
		super(t);
	}
	
	public MyDictionaryException(String cause, Throwable t) {
		super(cause, t);
	}

}
