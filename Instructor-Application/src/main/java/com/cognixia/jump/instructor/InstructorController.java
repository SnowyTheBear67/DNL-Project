package com.cognixia.jump.instructor;

<<<<<<< Updated upstream
import java.util.List;
import java.util.Optional;
=======

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
>>>>>>> Stashed changes

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InstructorController {
	
	@Autowired
<<<<<<< Updated upstream
	InstructorRepository service;
	
	//@Autowired
	//PasswordEncoder encoder;
	
	@GetMapping("/intructors")
=======
	PasswordEncoder encoder;
  
	@GetMapping("/instructors")
>>>>>>> Stashed changes
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
		
<<<<<<< Updated upstream
		System.out.println("Added: " + added);
=======
		// will need to encode the password ourselves before it gets saved to the DB
		// spring security won't know to do this automatically, so we need to make sure it
		// gets done anytime we create a new user
		instructor.setPassword( encoder.encode( instructor.getPassword() ) );
				
		
		Instructor created = service.save(instructor);
		
		return ResponseEntity.status(201).body(created);
>>>>>>> Stashed changes
		
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
<<<<<<< Updated upstream
	
=======
>>>>>>> Stashed changes
}
