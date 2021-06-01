package com.assignment.spring.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.spring.api.WeatherResponse;

@FeignClient(value = "weather-client", url = "${api.weather.service-url}",
		configuration = FeignConfiguration.class)
public interface WeatherAPIClient {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	WeatherResponse getWeather(@RequestParam("q") final String cityName);
}
