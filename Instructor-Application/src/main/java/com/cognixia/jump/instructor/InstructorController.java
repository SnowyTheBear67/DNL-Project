package com.cognixia.jump.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InstructorController {

	@Autowired 
	private InstructorRepository repo;
	
	@Autowired 
	private InstructorService service;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@PostMapping("/instructor")
	public ResponseEntity<?> createInstructor( @RequestBody Instructor instructor ) {
		
		instructor.setId(null);
		
		// will need to encode the password ourselves before it gets saved to the DB
		// spring security won't know to do this automatically, so we need to make sure it
		// gets done anytime we create a new user
		instructor.setPassword( encoder.encode( instructor.getPassword() ) );
				
		
		Instructor created = repo.save(instructor);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	
	
	
}
