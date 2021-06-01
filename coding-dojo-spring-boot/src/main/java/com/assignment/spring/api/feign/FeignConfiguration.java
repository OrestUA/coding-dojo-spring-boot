package com.assignment.spring.api.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfiguration {

	@Value("${api.weather.units: metric}")
	String units;

	@Value("${api.weather.app-id: }")
	String appId;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.query("appid", appId);
			requestTemplate.query("units", units);
		};
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}
}
