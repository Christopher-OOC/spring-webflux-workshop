package com.javalord.weather_playground_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.javalord.weather_playground_test.${sec}")
@EnableR2dbcRepositories(basePackages = "com.javalord.weather_playground_test.${sec}")
public class WeatherPlaygroundTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherPlaygroundTestApplication.class, args);
	}

}
