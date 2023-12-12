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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api")
@Tag(name = "Instructor Controller", description = "API for managing instructors")
public class InstructorController {

	@Autowired 
	private InstructorRepository service;
	
	@Autowired
	PasswordEncoder encoder;
  
  @Operation(summary="Get all the instructors from the instructor table")
  @GetMapping("/instructors")
	public List<Instructor> getAllInstructors(){
		return service.findAll();
	}
	
 
  @ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Instructor has been found", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class) ) ),
			@ApiResponse(responseCode = "404", description = "Instructor was not found", 
			 content = @Content ) 
			})
    @Operation(summary="Get instructor by id from the instructor table")
	@GetMapping("/instructors/{id}")
	public ResponseEntity<?> getInstructor(@PathVariable int id) throws ResourceNotFoundException {
		
		Optional<Instructor> instructor = service.findById(id);
		
		if(instructor.isPresent()) {
			return ResponseEntity.status(200).body(instructor.get());
		}
		throw new ResourceNotFoundException("Instructor", id);

	}
	
   
  	@ApiResponse(responseCode = "201", description = "Instructor has been created", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class) ) )
    @Operation(summary="Create instructor")
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
  	
  	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Instructor has been updated", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class) ) ),
			@ApiResponse(responseCode = "404", description = "Instructor was not found", 
			 content = @Content ) 
			})
    @Operation(summary="Update instructor in the instructor table")
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
	
  	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Instructor has been deleted", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class) ) ),
			@ApiResponse(responseCode = "404", description = "Instructor was not found", 
			 content = @Content ) 
			})
    @Operation(summary="Delete instructor by the specified id in the instructor table")
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
	

