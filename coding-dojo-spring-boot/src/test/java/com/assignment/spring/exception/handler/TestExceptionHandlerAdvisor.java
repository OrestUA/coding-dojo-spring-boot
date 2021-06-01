package com.assignment.spring.exception.handler;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.assignment.spring.controller.WeatherController;
import com.assignment.spring.exception.BadRequestException;
import com.assignment.spring.exception.NotFoundException;
import com.assignment.spring.exception.UnauthorizedException;

@ExtendWith(SpringExtension.class)
class TestExceptionHandlerAdvisor {

	private MockMvc mockMvc;

	@Mock
	private WeatherController weatherController;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController)
				.setControllerAdvice(new ExceptionHandlerAdvisor())
				.build();
	}

	@Test
	void testBadRequestExceptionHandler() throws Exception {

		when(weatherController.getSavedWeatherById(1)).thenThrow(new BadRequestException());

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Bad Request"))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test
	void testNotFoundExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(new NotFoundException());

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Not Found"))
				.andExpect(jsonPath("$.status").value("404"));
	}

	@Test
	void testUnauthorizedExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(new UnauthorizedException());

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message").value("Unauthorized"))
				.andExpect(jsonPath("$.status").value("401"));
	}

	@Test
	void testMethodArgumentTypeMismatchExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(MethodArgumentTypeMismatchException.class);

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Bad Request"))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test
	void testConstraintViolationExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(ConstraintViolationException.class);

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Bad Request"))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test
	void testHttpMessageNotReadableExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(HttpMessageNotReadableException.class);

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Bad Request"))
				.andExpect(jsonPath("$.status").value("400"));
	}

	@Test
	void testExceptionHandler() throws Exception {
		when(weatherController.getSavedWeatherById(1)).thenThrow(RuntimeException.class);

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andDo(print())
				.andExpect(status().isServiceUnavailable())
				.andExpect(jsonPath("$.message").value("Service Unavailable"))
				.andExpect(jsonPath("$.status").value("503"));
	}
}