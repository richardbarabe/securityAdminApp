package com.example.shiro.api;

public class UserDTO {
	private String username;
	private String password;
	private RoleDTO[] roles;
	
	public UserDTO() {}
	public UserDTO(String username, String password, RoleDTO... roles) {
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
	
	public RoleDTO[] getRoles() {
		return roles;
	}
	
	public void setRoles(RoleDTO[] roles) {
		this.roles = roles;
	}
}
