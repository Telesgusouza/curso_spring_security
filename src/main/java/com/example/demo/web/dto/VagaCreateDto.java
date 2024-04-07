package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VagaCreateDto {
	
	@NotBlank(message = "{NotBlank.vagaCreateDto.codigo}")
	@Size(min = 4, max = 4)
	private String codigo;
	
//	@NotBlank
	@Pattern(regexp = "LIVRE|OCUPADO")
	private String status;

	public VagaCreateDto() {}
	
	public VagaCreateDto(@Size(min = 4, max = 4) String codigo,
			@Pattern(regexp = "LIVRE|OCUPADO") String status) {
		super();
		this.codigo = codigo;
		this.status = status;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "VagaCreateDto [codigo=" + codigo + ", status=" + status + "]";
	}

	
	
}
