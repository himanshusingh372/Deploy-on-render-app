package com.example.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payloads.UserDto;
import com.example.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllers {

	@Autowired
	private UserService userServices;

	// post= create resource
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUser = this.userServices.createUser(userDto);
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	// put=update resource
	@PutMapping("/{userid}") // this is called path_Uri_Variable.
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userid") Integer id) {
		UserDto updateUser = this.userServices.updateUser(userDto, id);
		// if we use ("userid) so we can change id name
		return ResponseEntity.ok(updateUser);
	}

	//Only by Admin
	// Delete = delete resource
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userid) {
		this.userServices.deleteUser(userid);
		return new ResponseEntity<>(Map.of("message", "user deleted successfully"), HttpStatus.OK);
	}

	// Get= get users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userServices.getAllUsers());
	}

	// Get= get single user
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getAUser(@PathVariable Integer userid) {
		return ResponseEntity.ok(this.userServices.getUserById(userid));
	}
}
