package com.assignment.spring.service;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.WeatherEntity;

public interface WeatherService {
	WeatherEntity save(WeatherEntity weatherEntityResponse);

	WeatherEntity findById(int id);

	WeatherResponse getWeatherFromAPI(String cityName);

}