package com.example.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Integer fieldValue;
	String userValue;

	public ResourceNotFoundException(String resourceName, String fieldName, Integer id) {

		super(String.format("%s not found with %s : %d", resourceName, fieldName, id));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = id;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, String username) {
		super(String.format("%s not found with %s : %d", resourceName, fieldName, username));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.userValue = username;
	}

}
