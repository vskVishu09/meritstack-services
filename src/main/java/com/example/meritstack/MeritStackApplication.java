package com.example.meritstack;

import com.example.meritstack.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MeritStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeritStackApplication.class, args);
	}
}
