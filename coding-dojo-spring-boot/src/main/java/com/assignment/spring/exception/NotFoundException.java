package com.assignment.spring.exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException() {
		super("Not found");
	}
}
