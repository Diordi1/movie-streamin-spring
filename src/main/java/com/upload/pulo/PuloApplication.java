package com.upload.pulo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PuloApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuloApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer web() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedHeaders("*").allowedOrigins("http://localhost:5173/").allowedMethods("*");
				
			}

		};
	}
}
