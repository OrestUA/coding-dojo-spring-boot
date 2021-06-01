package com.assignment.spring.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.assignment.spring.api.Main;
import com.assignment.spring.api.Sys;
import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;

class TestWeatherEntityMapStructMapper {

	@Test
	void testResponseToResponseDto() {
		//Given
		final var weatherResponse = new WeatherResponse();
		final var sys = new Sys();
		sys.setCountry("UA");
		final var main = new Main();
		main.setTemp(17.05);

		weatherResponse.setName("Lviv");
		weatherResponse.setSys(sys);
		weatherResponse.setMain(main);

		//When
		final WeatherResponseDto responseDto = WeatherMapStructMapper.INSTANCE.responseToResponseDto(weatherResponse);

		//Then
		assertEquals("Lviv", responseDto.getCity());
		assertEquals("UA", responseDto.getCountry());
		assertEquals(17.05, responseDto.getTemperature());
	}

	@Test
	void testEntityToDto() {
		//Given
		final var weatherEntity = new WeatherEntity();
		weatherEntity.setCity("Lviv");
		weatherEntity.setCountry("UA");
		weatherEntity.setTemperature(17.05);

		//When
		final WeatherDto dto = WeatherMapStructMapper.INSTANCE.entityToDto(weatherEntity);

		//Then
		assertEquals("Lviv", dto.getCity());
		assertEquals("UA", dto.getCountry());
		assertEquals(17.05, dto.getTemperature());
	}

	@Test
	void testDtoToEntity() {
		//Given
		final var dto = new WeatherDto();
		dto.setCity("Lviv");
		dto.setCountry("UA");
		dto.setTemperature(17.05);

		//When
		final WeatherEntity entity = WeatherMapStructMapper.INSTANCE.dtoToEntity(dto);

		//Then
		assertEquals("Lviv", entity.getCity());
		assertEquals("UA", entity.getCountry());
		assertEquals(17.05, entity.getTemperature());
	}
}