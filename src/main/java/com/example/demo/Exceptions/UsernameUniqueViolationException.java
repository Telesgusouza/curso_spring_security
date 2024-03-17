package com.example.demo.Exceptions;

public class UsernameUniqueViolationException extends RuntimeException {
	private static final long serialVersionUID = 4855968193098823900L;
	
	public UsernameUniqueViolationException(String message) {
		super(message);
	}

}
