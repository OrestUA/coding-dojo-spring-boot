package com.assignment.spring.controller.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.spring.Application;
import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = Application.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WeatherControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WeatherRepository weatherRepository;

	@Test
	void testSaveWeatherDtoToDataBase() throws Exception {
		final WeatherDto weatherDto = createWeatherDto(null);
		final WeatherDto expectedSavedWeatherDto = createWeatherDto(1);

		mockMvc.perform(post("/api/weather")
				.content(objectMapper.writeValueAsString(weatherDto))
				.contentType("application/json"))
				.andExpect(status().is(201))
				.andExpect(content().json(objectMapper.writeValueAsString(expectedSavedWeatherDto)));

		Optional<WeatherEntity> weatherOptional = weatherRepository.findById(1);

		assertTrue(weatherOptional.isPresent());
		final WeatherEntity weatherEntity = weatherOptional.get();

		assertEquals(expectedSavedWeatherDto.getCity(), weatherEntity.getCity());
		assertEquals(expectedSavedWeatherDto.getCountry(), weatherEntity.getCountry());
		assertEquals(expectedSavedWeatherDto.getTemperature(), weatherEntity.getTemperature());
		assertEquals(expectedSavedWeatherDto.getId(), weatherEntity.getId());

	}

	@Test
	void testGetWeatherByCityNameFromAPI() throws Exception {
		mockMvc.perform(get("/api/weather/city/{cityName}", "Lviv"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.city").value("Lviv"))
				.andExpect(jsonPath("$.country").value("UA"));
	}

	@Test
	void testGetSavedWeatherByIdFromDataBase() throws Exception {
		final WeatherEntity weatherEntity = new WeatherEntity();
		weatherEntity.setCountry("John Doe Country");
		weatherEntity.setCity("John Doe City");
		weatherEntity.setTemperature(23.4);

		WeatherEntity savedEntity = weatherRepository.save(weatherEntity);

		mockMvc.perform(get("/api/weather/{cityId}", savedEntity.getId()))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(weatherEntity)));
	}

	private WeatherDto createWeatherDto(Integer id) {
		final WeatherDto weatherDto = new WeatherDto();
		weatherDto.setId(id);
		weatherDto.setCity("Lviv");
		weatherDto.setCountry("UA");
		weatherDto.setTemperature(15.06);
		return weatherDto;
	}

}
