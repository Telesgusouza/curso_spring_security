package com.example.demo.web.Exceptions;

public class CodigoUniqueViolationException extends RuntimeException {
	private static final long serialVersionUID = 5514000561282985382L;

	public CodigoUniqueViolationException(String msg) {
		super(msg);
	}

}
