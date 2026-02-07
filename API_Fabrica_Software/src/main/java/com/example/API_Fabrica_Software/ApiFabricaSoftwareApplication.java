package com.example.API_Fabrica_Software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ApiFabricaSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFabricaSoftwareApplication.class, args);

	}

}
