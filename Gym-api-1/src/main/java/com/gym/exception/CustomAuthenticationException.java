package com.gym.exception;

public class CustomAuthenticationException extends Exception {

	private String message;
	public CustomAuthenticationException(String string) {
		// TODO Auto-generated constructor stub
		this.message=string;
	}

}
