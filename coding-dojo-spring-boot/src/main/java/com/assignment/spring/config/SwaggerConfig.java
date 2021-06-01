package com.assignment.spring.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.assignment.spring.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}


	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Weather REST API Documentation",
				"Set of endpoints that requests data from https://openweathermap.org and stores the result in a database.",
				"1.0",
				null,
				new Contact("Yaroslav Skakun", "www.example.com", "myeaddress@company.com"),
				"License of API", "API license URL", Collections.emptyList());
	}
}
