package com.example.demo.web.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorMessage {

	// qual recurso gerou a exceção
	private String path;
	// qual método foi enviado
	private String method;
	// status que diz qual código de erro
	private int status;
	// mensagem do status
	private String statusText;
	// o que causou o erro
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, String> errors;

	public ErrorMessage() {
	}

	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		// getReasonPhrase é quem nos retorna um erro
		this.statusText = status.getReasonPhrase();
		this.message = message;
	}

	// BindingResult - quando uma de nossas validações der erro
	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		// getReasonPhrase é quem nos retorna um erro
		this.statusText = status.getReasonPhrase();
		this.message = message;
		addErrors(result);
	}

	public void addErrors(BindingResult result) {
		this.errors = new HashMap<>();

		for (FieldError fieldError : result.getFieldErrors()) {
			this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "ErrorMessage [path=" + path + ", method=" + method + ", status=" + status + ", statusText=" + statusText
				+ ", message=" + message + ", errors=" + errors + "]";
	}

}