package com.example.blog.exceptions;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String FieldName = ((FieldError) error).getField();
			String Message = error.getDefaultMessage();
			resp.put(FieldName, Message);
		});

		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiExpection(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse response=new ApiResponse(message,false);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ApiResponse> InternalAuthenticationExceptionHandler(InternalAuthenticationServiceException ex) {
		String msg="user not found";
		ApiResponse response=new ApiResponse(msg,false);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ApiResponse> FileAlreadyExistsExceptionHandler(FileAlreadyExistsException ex) {
		String msg="Email is Already Exist ";
		ApiResponse response=new ApiResponse(msg,false);
		return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
	}
}
