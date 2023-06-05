package com.example.blog.services;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

import com.example.blog.payloads.UserDto;

public interface UserService {
	
UserDto registerNewuser(UserDto user) throws FileAlreadyExistsException;
UserDto createUser(UserDto user);
UserDto updateUser(UserDto user,Integer id);
UserDto getUserById(Integer id);
List<UserDto> getAllUsers();
void deleteUser(Integer id);
}
