package com.example.blog.exceptions;

@SuppressWarnings("serial")
public class ApiException extends RuntimeException{

	public ApiException() {
		super();
		
	}

	public ApiException(String message) {
		super(message);
		
	}

	
}
