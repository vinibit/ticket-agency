package com.packtpub.as7development.chapter3.ejb;

public class NotEnoughMoneyException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughMoneyException(String string) {
		super(string);
	}

}
