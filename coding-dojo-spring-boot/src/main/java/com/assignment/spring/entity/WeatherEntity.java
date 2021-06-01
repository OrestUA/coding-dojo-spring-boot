package com.assignment.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather")
@Data
@NoArgsConstructor
public class WeatherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "City is mandatory")
	private String city;

	@NotBlank(message = "Country is mandatory")
	@Size(min = 2, message = "Country code at least 2 symbols")
	private String country;

	@NotNull(message = "Temperature is mandatory")
	@Digits(integer = 5, fraction = 2)
	@Column(columnDefinition = "NUMERIC(5, 2)")
	private Double temperature;
}
