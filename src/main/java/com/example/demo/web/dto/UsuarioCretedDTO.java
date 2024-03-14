package com.example.demo.web.dto;

public class UsuarioCretedDTO {

	private String username;
	private String password;
	
	public UsuarioCretedDTO() {}
	
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
