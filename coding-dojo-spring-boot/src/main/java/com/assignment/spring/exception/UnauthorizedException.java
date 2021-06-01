package com.assignment.spring.exception;

public class UnauthorizedException extends RuntimeException {
	public UnauthorizedException() {
		super("Failed authorization");
	}
}
