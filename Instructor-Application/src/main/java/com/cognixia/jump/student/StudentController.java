package com.cognixia.jump.student;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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
@Tag(name = "Student Controller", description = "API for managing students")
public class StudentController {

	@Autowired
	StudentService service;
	
	
	@Operation(summary = "Get all the students from the student table")
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return service.getAllStudents();
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student has been found", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class) ) ),
			@ApiResponse(responseCode = "404", description = "Student was not found", 
			 			 content = @Content ) 
		}
	)
	@Operation(summary = "Get student by the specified id from the student table")
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable int id) throws ResourceNotFoundException {
		
		Student student = service.getStudent(id);
		
		return ResponseEntity.status(200).body(student);
	}
	
	
	
	@ApiResponse(responseCode = "201", description = "Student has been created", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class) ) )
	@Operation(summary = "Add student to the student table")
	@PostMapping("/students/add")
	public ResponseEntity<?> addStudent(@Valid @RequestBody Student newStudent) throws ResourceNotFoundException{
		
		Student added = service.addStudent(newStudent);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student has been updated", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class) ) ),
			@ApiResponse(responseCode = "404", description = "Student was not found", 
			 			 content = @Content ) 
		}
	)
	@Operation(summary = "Update student with the specified id in the student table")
	@PutMapping("students/update")
	public ResponseEntity<?> updateStudent(@Valid @RequestBody Student updateStudent) throws ResourceNotFoundException {
		
		Student updated = service.updateStudent(updateStudent);
		
		return ResponseEntity.status(200).body(updateStudent);
		
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Student has been deleted", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class) ) ),
			@ApiResponse(responseCode = "404", description = "Student was not found", 
			 			 content = @Content ) 
		}
	)
	@Operation(summary = "Delete student by specified id in the student table")
	@DeleteMapping("students/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable int id) throws ResourceNotFoundException{
		
		service.deleteStudent(id);
		
		return ResponseEntity.status(200).body("Deleted Student with id = " + id);
		
	}
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Students found", 
						 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class) ) ),
			@ApiResponse(responseCode = "404", description = "Students not found", 
			 			 content = @Content ) 
		}
	)
	@Operation(summary = "Get the students by specified instructor username")
	@GetMapping("/students/instructor/{username}")
    public ResponseEntity<?> getStudentsByInstructorUsername(@PathVariable String username) throws ResourceNotFoundException {

		List<Student> students = service.getStudentsByInstructorUsername(username);
		
		return ResponseEntity.status(200).body(students);
    }
}
