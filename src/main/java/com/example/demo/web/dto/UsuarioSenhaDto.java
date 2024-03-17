package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDto {

	@NotBlank
	@Size(min = 6, max = 18)
	private String senhaAtual;
	@NotBlank
	@Size(min = 6, max = 18)
	private String novaSenha;
	@NotBlank
	@Size(min = 6, max = 18)
	private String confimaSenha;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfimaSenha() {
		return confimaSenha;
	}

	public void setConfimaSenha(String confimaSenha) {
		this.confimaSenha = confimaSenha;
	}

}
