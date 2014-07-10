package com.rp.example.api.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.rp.example.api.UserDTO;
import com.rp.example.api.UserService;

@Controller
public class JsonUserService {
	
	@Autowired
	UserService userService;
	
	/**
	 * Search for users.  Uses pagination.
	 * @param searchString Search by username.  Any username containing "searchString" will be returned.
	 * @param page Page number
	 * @param pageSize Size of the pages.
	 * @return
	 */
	@RequestMapping(value="/api/v1/security/users", method=RequestMethod.GET)
	@ResponseBody
	public List<UserDTO> search(@RequestParam(defaultValue="")String searchString, @RequestParam Integer page, @RequestParam Integer pageSize) {
//		SecurityUtils.getSubject().checkRole("SYSTEM_ADMIN");
		return userService.queryByUsername(searchString, page, pageSize);
	}
	
	@RequestMapping(value="/api/v1/security/users", method=RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody UserDTO user, UriComponentsBuilder builder) {
//		SecurityUtils.getSubject().checkRole("SYSTEM_ADMIN");
		userService.create(user.getUsername(), user.getRoles());
		
		UriComponents uriComponents = builder.path(
				"/api/v1/security/users/{name}").buildAndExpand(
				user.getUsername());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
}
