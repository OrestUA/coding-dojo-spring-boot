package com.assignment.spring.controller.it;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.assignment.spring.mapper.WeatherMapStructMapper;
import com.assignment.spring.service.WeatherService;
import com.assignment.spring.service.impl.WeatherServiceImpl;

@TestConfiguration
public class WeatherControllerTestContextConfiguration {

//	@Bean
//	public WeatherService weatherService(){
//		return new WeatherServiceImpl();
//	}

	public WeatherMapStructMapper mapStructMapper;
}
