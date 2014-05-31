package com.example.shiro.api;

public class UserDTO {
	private String username;
	private String password;
	private String[] roles;
	
	public UserDTO() {}
	public UserDTO(String username, String... roles) {
		this.username = username;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String[] getRoles() {
		return roles;
	}
	
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}
