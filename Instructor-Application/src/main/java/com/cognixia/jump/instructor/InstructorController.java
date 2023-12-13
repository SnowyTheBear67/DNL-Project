package com.cognixia.jump.instructor;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
	private InstructorService service;
	
	@Autowired
	PasswordEncoder encoder;
  
	@GetMapping("/instructors")
	public List<Instructor> getAllInstructors(){
		return service.getAllInstructors();
	}
	
	@GetMapping("/instructors/{id}")
	public ResponseEntity<?> getInstructor(@PathVariable int id) throws ResourceNotFoundException {
		
		Instructor found = service.getInstructor(id);
		
		return ResponseEntity.status(200).body(found);

	}
	
	
	@PostMapping("/instructors")
	public ResponseEntity<?> createInstructor(@Valid @RequestBody Instructor instructor ) {
		
		Instructor created = service.createInstructor(instructor);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	@PutMapping("/instructors/update")
	public ResponseEntity<?> updateInstructor(@RequestBody Instructor updateInstructor) throws ResourceNotFoundException{
		
		Instructor updated = service.updateInstructor(updateInstructor);
		
		return ResponseEntity.status(200).body(updated);
		
	}
	
	@DeleteMapping("/instructors/delete/{id}")
	public ResponseEntity<?> deleteInstructor(@PathVariable int id) throws ResourceNotFoundException {
		
		service.deleteInstructor(id);
		
		return ResponseEntity.status(200).body("Deleted Instructor with id = " + id);
		
	}
}
	

