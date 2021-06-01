package com.assignment.spring.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.api.feign.WeatherAPIClient;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;

@ExtendWith(MockitoExtension.class)
class TestWeatherServiceImpl {

	@InjectMocks
	private WeatherServiceImpl weatherService;

	@Mock
	private WeatherRepository weatherRepositoryMock;

	@Mock
	private WeatherAPIClient weatherAPIClientMock;

	private final WeatherEntity weatherEntity = createWeatherEntity(null);

	@Test
	void testSave() {
		//Given
		final WeatherEntity savedEntity = createWeatherEntity(1);

		when(weatherRepositoryMock.save(this.weatherEntity)).thenReturn(savedEntity);

		//When
		final WeatherEntity actualSavedEntity = weatherService.save(this.weatherEntity);

		//Then
		assertNotNull(actualSavedEntity);
		assertEquals(savedEntity, actualSavedEntity);
	}

	@Test
	void testFindByIdReturnsSavedEntity() {
		//Given
		final WeatherEntity savedEntity = createWeatherEntity(1);

		when(weatherRepositoryMock.findById(1)).thenReturn(Optional.of(savedEntity));

		//When
		final WeatherEntity actualSavedEntity = weatherService.findById(1);

		//Then
		assertNotNull(actualSavedEntity);
		assertEquals(savedEntity, actualSavedEntity);
	}

	@Test
	void testFindByIdReturnsEmptyEntityWhenNotFound() {
		//Given
		when(weatherRepositoryMock.findById(1)).thenReturn(Optional.empty());

		//When
		final WeatherEntity actualSavedEntity = weatherService.findById(1);

		//Then
		assertNotNull(actualSavedEntity);
		assertNull(actualSavedEntity.getId());
		assertNull(actualSavedEntity.getCountry());
		assertNull(actualSavedEntity.getCity());
		assertNull(actualSavedEntity.getTemperature());
	}

	@Test
	void testGetWeatherFromAPI() {
		//Given
		final WeatherResponse weatherResponse = new WeatherResponse();
		weatherResponse.setId(123);

		when(weatherAPIClientMock.getWeather("Lviv")).thenReturn(weatherResponse);

		//When
		final WeatherResponse actualWeatherResponse = weatherService.getWeatherFromAPI("Lviv");

		//Then
		assertNotNull(actualWeatherResponse);
		assertEquals(123, actualWeatherResponse.getId());
	}

	private WeatherEntity createWeatherEntity(Integer id) {
		final WeatherEntity weatherEntity = new WeatherEntity();
		weatherEntity.setId(id);
		weatherEntity.setCity("Lviv");
		weatherEntity.setCountry("UA");
		weatherEntity.setTemperature(15.06);
		return weatherEntity;
	}
}