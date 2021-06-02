package com.assignment.spring.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.mapper.WeatherMapStructMapper;
import com.assignment.spring.service.WeatherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/weather")
@Api(value = "WeatherController")
@Slf4j
@AllArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	private final WeatherMapStructMapper mapStructMapper;

	//NOTE: all these todos will be added in the following commits!!!!!!!
	//TODO add security OAuth2??? JWT??

	//TODO add fallback when downstream service is unawailable provide some response?

	//TODO secure actuator

	//TODO add JSON sanitizer ??

	//TODO add intergration test for profiles H2 and Postgress

	//TODO add integration tests for repository

	//TODO add Dockerfile and configure externalization of properties and variables, docker compose for Postgres???

	//TODO update readme file

	@ApiOperation(value = "Saves weather report into a database", response = WeatherDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Saved successfully!"),
			@ApiResponse(code = 401, message = "Not authorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not found!"),
			@ApiResponse(code = 503, message = "Unavailable!")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WeatherDto saveWeather(@Valid @RequestBody final WeatherDto weatherDto) {
		log.info("In WeatherController.saveWeather {} ", weatherDto);
		final var weather = mapStructMapper.dtoToEntity(weatherDto);
		final var savedWeather = weatherService.save(weather);
		return mapStructMapper.entityToDto(savedWeather);
	}

	@ApiOperation(value = "Gets weather report from https://openweathermap.org/ API by city name",
			response = WeatherResponseDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Saved successfully!"),
			@ApiResponse(code = 401, message = "Not authorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not found!"),
			@ApiResponse(code = 503, message = "Unavailable!")})
	@GetMapping("/city/{cityName}")
	@ResponseStatus(HttpStatus.OK)
	public WeatherResponseDto getWeatherByCityName(@PathVariable final String cityName) {
		log.info("In WeatherController.getWeatherByCityName {} ", cityName);
		final var weatherResponse = weatherService.getWeatherFromAPI(cityName);
		log.info("Received WeatherResponse from API {} ", weatherResponse);
		return mapStructMapper.responseToResponseDto(weatherResponse);
	}

	@ApiOperation(value = "Gets saved weather report from a database by its id",
			response = WeatherResponseDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Saved successfully!"),
			@ApiResponse(code = 401, message = "Not authorized!"),
			@ApiResponse(code = 403, message = "Forbidden!"),
			@ApiResponse(code = 404, message = "Not found!"),
			@ApiResponse(code = 503, message = "Unavailable!")})
	@GetMapping("/{cityId}")
	@ResponseStatus(HttpStatus.OK)
	public WeatherDto getSavedWeatherById(@PathVariable final int cityId) {
		log.info("In WeatherController.getSavedWeatherById {} ", cityId);
		final var weather = weatherService.findById(cityId);
		return mapStructMapper.entityToDto(weather);
	}
}