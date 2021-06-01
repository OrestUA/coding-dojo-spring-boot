package com.assignment.spring.exception.handler;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.assignment.spring.dto.ErrorMessageDto;
import com.assignment.spring.exception.BadRequestException;
import com.assignment.spring.exception.NotFoundException;
import com.assignment.spring.exception.UnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvisor {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDto badRequestExceptionHandler(BadRequestException ex) {
		log.error(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessageDto notFoundExceptionHandler(NotFoundException ex) {
		log.error(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorMessageDto unauthorizedExceptionHandler(UnauthorizedException ex) {
		log.error(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDto methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
		log.error(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDto constraintViolationExceptionHandler(ConstraintViolationException ex) {
		log.warn(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessageDto httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
		log.warn(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
	public ErrorMessageDto exceptionHandler(Exception ex) {
		log.error(ex.getMessage());
		return new ErrorMessageDto(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
	}
}