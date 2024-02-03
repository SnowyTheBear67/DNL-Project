package com.cognixia.jump.instructor;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
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

@Validated
@RestController
@RequestMapping("/api")
@Tag(name = "Instructor Controller", description = "API for managing instructors")
public class InstructorController {

	@Autowired 
	private InstructorService service;
	
	@Autowired
	PasswordEncoder encoder;
  


  @Operation(summary="Get all the instructors from the instructor table")
  @GetMapping("/instructors")
	public List<Instructor> getAllInstructors(){
		return service.getAllInstructors();
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
		
		Instructor found = service.getInstructor(id);
		
		return ResponseEntity.status(200).body(found);

	}

  	@ApiResponse(responseCode = "201", description = "Instructor has been created", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Instructor.class) ) )
    @Operation(summary="Create instructor")
	@PostMapping("/instructors")
	public ResponseEntity<?> createInstructor(@RequestBody @Valid Instructor instructor ) {
		
		Instructor created = service.createInstructor(instructor);
		
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
	public ResponseEntity<?> updateInstructor(@RequestBody @Valid Instructor updateInstructor) throws ResourceNotFoundException{
		
		Instructor updated = service.updateInstructor(updateInstructor);
		
		return ResponseEntity.status(200).body(updated);
		
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
		
		service.deleteInstructor(id);
		
		return ResponseEntity.status(200).body("Deleted Instructor with id = " + id);
		
	}
}
	

