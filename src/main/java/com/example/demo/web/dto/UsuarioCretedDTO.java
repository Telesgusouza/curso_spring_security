package com.example.demo.web.dto;

import jakarta.validation.constraints.Email;
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
public class UsuarioCretedDTO {

	@NotBlank
	@Email(message = "Formato do e-mail está invalido")
	private String username;
	@NotBlank
	@Size(min = 6, max = 18)
	private String password;

	public UsuarioCretedDTO() {
	}

	public UsuarioCretedDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UsuarioCretedDTO [username=" + username + ", password=" + password + "]";
	}

}
