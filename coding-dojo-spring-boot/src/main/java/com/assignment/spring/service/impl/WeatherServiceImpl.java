package com.assignment.spring.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.api.feign.WeatherAPIClient;
import com.assignment.spring.entity.WeatherEntity;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class WeatherServiceImpl implements WeatherService {

	private final WeatherAPIClient weatherAPIClient;

	private final WeatherRepository weatherRepository;

	public WeatherServiceImpl(WeatherAPIClient weatherAPIClient, WeatherRepository weatherRepository) {
		this.weatherAPIClient = weatherAPIClient;
		this.weatherRepository = weatherRepository;
	}

	@Override
	public WeatherEntity save(WeatherEntity weatherEntity) {
		log.info("In WeatherService.save {} ", weatherEntity);
		final var savedWeather = weatherRepository.save(weatherEntity);
		log.info("Saved weather entity {} ", weatherEntity);
		return savedWeather;
	}

	@Cacheable("weather")
	@Transactional(readOnly = true)
	@Override
	public WeatherEntity findById(int id) {
		log.info("In WeatherService.findById {} ", id);
		final var weather = weatherRepository.findById(id).orElse(new WeatherEntity());
		log.info("Found weather entity in database{} ", weather);
		return weather;
	}

	@Cacheable("weather-api")
	@Override
	public WeatherResponse getWeatherFromAPI(String cityName) {
		log.info("In WeatherService.getWeatherFromAPI {} ", cityName);
		final var weatherResponse = weatherAPIClient.getWeather(cityName);
		log.info("Found weather via API{} ", weatherResponse);
		return weatherResponse;
	}

}
