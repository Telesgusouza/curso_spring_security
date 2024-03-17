package com.example.demo.Exceptions;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3241639509501806332L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}
