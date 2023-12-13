package com.cognixia.jump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		// provides meta data on the API service
		info = @Info(
					title = "Instructor Dashboard API", // title of the Documentation page
					version = "1.0",	// version of your API
					description = "API that allows instructors to authenticate, view, create, update and delete students.",
					contact = @Contact(name = "Cognixia", email = "jumpspartans@cognixia.com", url = "https://www.collabera.com"),
					license = @License(name = "Instructor Dashboard License v1.0", url = "https://www.collabera.com/"),
					termsOfService = "https://www.collabera.com/" // must be a url
				),
		externalDocs = @ExternalDocumentation(description = "More info on the Instructor Dashboard API", url = "https://www.google.com/")
		

)
public class InstructorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstructorApplication.class, args);
	}
}
