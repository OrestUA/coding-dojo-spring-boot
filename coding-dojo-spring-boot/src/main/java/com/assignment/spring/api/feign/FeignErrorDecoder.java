package com.assignment.spring.api.feign;

import com.assignment.spring.exception.BadRequestException;
import com.assignment.spring.exception.NotFoundException;
import com.assignment.spring.exception.UnauthorizedException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
			case 400:
				return new BadRequestException();
			case 401:
				return new UnauthorizedException();
			case 404:
				return new NotFoundException();
			default:
				return new RuntimeException();
		}
	}
}
