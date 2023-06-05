package com.example.blog.services.impl;


import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Post;
import com.example.blog.entities.Role;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.UserDto;
import com.example.blog.repositories.RoleRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User Saveduser = this.userRepo.save(user);
		return this.userToDto(Saveduser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = this.userRepo.findById(id) // to check the id is exist or not
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
	

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userdto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userdto;
	}
//
//	@Override
//	public void deleteUser(Integer id) {
//		User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//		this.userRepo.delete(user);

//}
	
	@Override
	public void deleteUser(Integer id) {
	    User user = userRepo.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

	    // Remove associations between user and posts
	    for (Post post : user.getPosts()) {
	        post.setUser(null);
	    }
	    user.getPosts().clear();

	    // Remove associations between user and roles
	    user.getRoles().clear();

	    // Delete the user
	    userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setName(userDto.getName());
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setName(user.getName());
//		userDto.setId(user.getId());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
	}

	@Override
	public UserDto registerNewuser(UserDto userDto) throws  FileAlreadyExistsException {
		
		User user = this.modelMapper.map(userDto,User.class);
		
		boolean  registeredUser = userRepo.existsByEmail(user.getEmail());
		try {
		    if (registeredUser == true) {
		     
		        // Handle the case where the user is already registered
		        throw new FileAlreadyExistsException("User already registered");
		    }
		} catch (FileAlreadyExistsException e) {
		    // Handle the exception if needed or rethrow it
		    throw new FileAlreadyExistsException("User already registered");
		}
       
            

		 // encode the password
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			
			//roles
			Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			user.getRoles().add(role);
		    User saved = this.userRepo.save(user);
		    // Handle successful user registration
		    return this.modelMapper.map(saved, UserDto.class);
		    
		
	}
}
