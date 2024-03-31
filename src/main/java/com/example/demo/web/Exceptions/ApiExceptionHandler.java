package com.example.demo.web.Exceptions;

import java.nio.file.AccessDeniedException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Exceptions.CpfUniqueViolationException;
import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.Exceptions.UsernameUniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

// lombok
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> accessDeniedException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.FORBIDDEN, "Usuario não encontrado"));// ex.getMessage()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> entityNotFoundException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND, "Usuario não encontrado"));// ex.getMessage()));
	}

	// dizemos qual exceção ele devera pegar
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request, BindingResult result) {

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "campos invalidos", result));
	}

	@ExceptionHandler({ UsernameUniqueViolationException.class, CpfUniqueViolationException.class, CodigoUniqueViolationException.class })
	public ResponseEntity<ErrorMessage> usernameUniqueViolationException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, "Email já existe"));// ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorMessage> dataIntegrityViolationException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT, "Email já existe"));// ex.getMessage()));
	}

}
