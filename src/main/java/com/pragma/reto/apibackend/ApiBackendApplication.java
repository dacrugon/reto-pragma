package com.pragma.reto.apibackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.pragma.reto.apibackend.models.repository")
@EnableJpaRepositories("com.pragma.reto.apibackend.models.repository")
public class ApiBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
