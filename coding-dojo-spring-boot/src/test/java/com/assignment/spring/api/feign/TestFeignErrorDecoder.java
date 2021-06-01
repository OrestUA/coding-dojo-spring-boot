package com.assignment.spring.api.feign;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.spring.exception.BadRequestException;
import com.assignment.spring.exception.NotFoundException;
import com.assignment.spring.exception.UnauthorizedException;

import feign.Request;
import feign.Response;

@ExtendWith(MockitoExtension.class)
class TestFeignErrorDecoder {

	@InjectMocks
	private FeignErrorDecoder feignErrorDecoder;

	private static final String METHOD_KEY = "method";

	private static final Map<String, Collection<String>> HEADERS_ERROR = new HashMap<>();

	@Test
	void testDecode400ResponseCodeReturnsBadRequestException() {
		//Given
		final Response response = createResponse(400);

		//When
		final Exception exception = feignErrorDecoder.decode(METHOD_KEY, response);

		//Then
		assertThat(exception, Matchers.instanceOf(BadRequestException.class));

	}

	@Test
	void testDecode401ResponseCodeReturnsUnauthorizedException() {
		//Given
		final Response response = createResponse(401);

		//When
		final Exception exception = feignErrorDecoder.decode(METHOD_KEY, response);

		//Then
		assertThat(exception, Matchers.instanceOf(UnauthorizedException.class));
	}

	@Test
	void testDecode404ResponseCodeReturnsNotFoundException() {
		//Given
		final Response response = createResponse(404);

		//When
		final Exception exception = feignErrorDecoder.decode(METHOD_KEY, response);

		//Then
		assertThat(exception, Matchers.instanceOf(NotFoundException.class));
	}

	@Test
	void testDecodeDefaultCaseReturnsRuntimeException() {
		//Given
		final Response response = createResponse(500);

		//When
		final Exception exception = feignErrorDecoder.decode(METHOD_KEY, response);

		//Then
		assertThat(exception, Matchers.instanceOf(RuntimeException.class));
	}

	private static Response createResponse(int status) {
		return Response.builder().status(status).request(Request.create(
				Request.HttpMethod.GET,
				"weather/1",
				HEADERS_ERROR,
				null,
				null,
				null))
				.build();
	}
}