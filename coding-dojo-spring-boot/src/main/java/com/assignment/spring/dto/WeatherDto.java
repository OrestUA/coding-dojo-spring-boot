package com.assignment.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

	@ApiModelProperty(notes = "ID of the weather record in a database", name = "id")
	private Integer id;

	@ApiModelProperty(notes = "City to get weather report of", name = "city", required = true)
	private String city;

	@ApiModelProperty(notes = "Country code of the city located in", name = "country", required = true)
	private String country;

	@ApiModelProperty(notes = "Weather temperature", name = "temperature", required = true)
	private Double temperature;
}
