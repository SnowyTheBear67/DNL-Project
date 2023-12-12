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

@RestController
@RequestMapping("/api")
public class InstructorController {
	
	@Autowired
	InstructorRepository service;
	
	//@Autowired
	//PasswordEncoder encoder;
	
	@GetMapping("/intructors")
	public List<Instructor> getAllInstructors(){
		return service.findAll();
	}
	
	@GetMapping("/instructors/{id}")
	public ResponseEntity<?> getInstructor(@PathVariable int id){
		
		Optional<Instructor> instructor = service.findById(id);
		
		if(instructor.isPresent()) {
			return ResponseEntity.status(200).body(instructor.get());
		}
		
		return ResponseEntity.status(404).body("Instructor with id  = " + id + " was not found.");
	}
	
	@PostMapping("/instructors/add")
	public ResponseEntity<?> addInstructor(@RequestBody Instructor newInstructor){
		
		newInstructor.setId(null);

		Instructor added = service.save(newInstructor);
		
		System.out.println("Added: " + added);
		
		return ResponseEntity.status(201).body(added);
	}
	
	@PutMapping("instructors/update")
	public ResponseEntity<?> updateInstructor(@RequestBody Instructor updateInstructor){
		//check if student exists, then update them
		
		Optional<Instructor> found = service.findById(updateInstructor.getId());
		
		if(found.isPresent()) {
			Instructor updated = service.save(updateInstructor);
			return ResponseEntity.status(200).body(updated);
		}
		else {
			return ResponseEntity.status(404).body("Instructor with id = " + updateInstructor.getId() + " was not found.");
		}
		
	}
	
	@DeleteMapping("instructors/delete/{id}")
	public ResponseEntity<?> deleteInstructor(@PathVariable int id){
		
		Optional<Instructor> found = service.findById(id);
		
		if(found.isPresent()) {
			service.deleteById(id);
			
			return ResponseEntity.status(200).body(found.get());
		}
		else {
			return ResponseEntity.status(404).body("Instructor with id = " + id + " not found.");
		}
		
	}
	
}
