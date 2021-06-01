package com.assignment.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.mapper.WeatherMapStructMapper;
import com.assignment.spring.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherController.class)
class TestWeatherController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private WeatherService weatherServiceMock;

	@MockBean
	private WeatherMapStructMapper mapStructMapperMock;

	@Test
	void testSaveWeather() throws Exception {
		final WeatherEntity weatherEntity = new WeatherEntity();
		final WeatherDto weatherDto = new WeatherDto();

		when(weatherServiceMock.save(weatherEntity)).thenReturn(weatherEntity);
		when(mapStructMapperMock.dtoToEntity(weatherDto)).thenReturn(weatherEntity);
		when(mapStructMapperMock.entityToDto(weatherEntity)).thenReturn(weatherDto);

		mockMvc.perform(post("/api/weather")
				.content(objectMapper.writeValueAsString(weatherDto))
				.contentType("application/json"))
				.andExpect(status().is(201))
				.andExpect(content().json(objectMapper.writeValueAsString(weatherDto)));
	}

	@Test
	void testGetWeatherByCityName() throws Exception {
		final WeatherResponse weatherResponse = new WeatherResponse();
		final WeatherResponseDto weatherResponseDto = new WeatherResponseDto();

		when(weatherServiceMock.getWeatherFromAPI("Lviv")).thenReturn(weatherResponse);
		when(mapStructMapperMock.responseToResponseDto(weatherResponse)).thenReturn(weatherResponseDto);

		mockMvc.perform(get("/api/weather/city/{cityName}", "Lviv"))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(weatherResponseDto)));
	}

	@Test
	void testGetSavedWeatherById() throws Exception {
		final WeatherEntity weatherEntity = new WeatherEntity();
		final WeatherDto weatherDto = new WeatherDto();

		when(weatherServiceMock.findById(1)).thenReturn(weatherEntity);
		when(mapStructMapperMock.entityToDto(weatherEntity)).thenReturn(weatherDto);

		mockMvc.perform(get("/api/weather/{cityId}", 1))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(weatherDto)));
	}
}