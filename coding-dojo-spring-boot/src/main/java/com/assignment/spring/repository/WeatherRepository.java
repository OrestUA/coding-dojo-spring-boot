package com.assignment.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.spring.entity.WeatherEntity;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
}
