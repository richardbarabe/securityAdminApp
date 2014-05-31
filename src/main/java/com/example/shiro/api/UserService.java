package com.example.shiro.api;

import java.util.List;

public interface UserService {
	public void create(String username, String... roles);
	public UserDTO get(String username);
	public void delete(String username);
	public void update(UserDTO user);
	public List<UserDTO> queryByUsername(String searchString, int pageNumber, int pageSize);
}
