package com.example.demo.globalExpceptionHandler;

//runtime custom exception handling class
@SuppressWarnings("serial")
public class CustomException extends RuntimeException {
	public CustomException(String message) {
		super(message);
	}
}
