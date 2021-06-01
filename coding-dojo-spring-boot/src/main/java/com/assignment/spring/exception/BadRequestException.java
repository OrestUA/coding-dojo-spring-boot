package com.assignment.spring.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException() {
		super("Bad request");
	}
}
