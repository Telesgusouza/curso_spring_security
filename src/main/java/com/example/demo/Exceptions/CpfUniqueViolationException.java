package com.example.demo.Exceptions;

public class CpfUniqueViolationException extends RuntimeException {
	private static final long serialVersionUID = 3262803732485486321L;
	
	public CpfUniqueViolationException(String message) {
		super(message);
		
		System.out.println("====================================");
		System.out.println("BOOOORA AMIGO");
		
	}
	
}
