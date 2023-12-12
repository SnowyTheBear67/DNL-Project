package com.cognixia.jump.instructor;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class InstructorController {

	@Autowired 
	private InstructorRepository service;
	
	@Autowired
	PasswordEncoder encoder;
  
  @GetMapping("/instructors")
	public List<Instructor> getAllInstructors(){
		return service.findAll();
	}
	
	@GetMapping("/instructors/{id}")
	public ResponseEntity<?> getInstructor(@PathVariable int id) throws ResourceNotFoundException {
		
		Optional<Instructor> instructor = service.findById(id);
		
		if(instructor.isPresent()) {
			return ResponseEntity.status(200).body(instructor.get());
		}
		throw new ResourceNotFoundException("Instructor", id);

	}
	
	
	@PostMapping("/instructor")
	public ResponseEntity<?> createInstructor( @RequestBody Instructor instructor ) {
		
		instructor.setId(null);
		
		// will need to encode the password ourselves before it gets saved to the DB
		// spring security won't know to do this automatically, so we need to make sure it
		// gets done anytime we create a new user
		instructor.setPassword( encoder.encode( instructor.getPassword() ) );
				

		Instructor created = service.save(instructor);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	@PutMapping("instructors/update")
	public ResponseEntity<?> updateInstructor(@RequestBody Instructor updateInstructor) throws ResourceNotFoundException{
		
		//check if student exists, then update them
		
		Optional<Instructor> found = service.findById(updateInstructor.getId());
		
		if(found.isPresent()) {
			Instructor updated = service.save(updateInstructor);
			return ResponseEntity.status(200).body(updated);
		}
		else {
			throw new ResourceNotFoundException("Instructor", updateInstructor.getId());
		}
		
	}
	
	@DeleteMapping("instructors/delete/{id}")
	public ResponseEntity<?> deleteInstructor(@PathVariable int id) throws ResourceNotFoundException {
		
		Optional<Instructor> found = service.findById(id);
		
		if(found.isPresent()) {
			service.deleteById(id);
			
			return ResponseEntity.status(200).body(found.get());
		}
		else {
			throw new ResourceNotFoundException("Instructor", id);
		}
		
	}
}
	

