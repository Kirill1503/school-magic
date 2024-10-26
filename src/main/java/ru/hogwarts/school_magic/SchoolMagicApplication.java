package ru.hogwarts.school_magic;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SchoolMagicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolMagicApplication.class, args);
	}
}
