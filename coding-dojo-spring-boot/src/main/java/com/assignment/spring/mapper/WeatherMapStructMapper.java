package com.assignment.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.dto.WeatherDto;
import com.assignment.spring.dto.WeatherResponseDto;
import com.assignment.spring.entity.WeatherEntity;

@Mapper(componentModel = "spring")
public interface WeatherMapStructMapper {

	WeatherMapStructMapper INSTANCE = Mappers.getMapper(WeatherMapStructMapper.class);

	@Mapping(target = "city", source = "response.name")
	@Mapping(target = "country", source = "response.sys.country")
	@Mapping(target = "temperature", source = "response.main.temp")
	WeatherResponseDto responseToResponseDto(WeatherResponse response);

	WeatherDto entityToDto(WeatherEntity entity);

	WeatherEntity dtoToEntity(WeatherDto weatherDto);
}
