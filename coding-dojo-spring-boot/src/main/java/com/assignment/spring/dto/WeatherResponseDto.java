package com.assignment.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {

	@ApiModelProperty(notes = "City to get weather report of", name = "city")
	private String city;

	@ApiModelProperty(notes = "Country code of the city located in", name = "country")
	private String country;

	@ApiModelProperty(notes = "Weather temperature", name = "temperature")
	private Double temperature;
}
