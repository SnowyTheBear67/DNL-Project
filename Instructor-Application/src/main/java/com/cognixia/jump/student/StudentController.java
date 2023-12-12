package com.cognixia.jump.student;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
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
public class StudentController {

	@Autowired
	StudentRepository service;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return service.findAll();
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable int id){
		
		Optional<Student> student = service.findById(id);
		
		if(student.isPresent()) {
			return ResponseEntity.status(200).body(student.get());
		}
		
		return ResponseEntity.status(404).body("Student with id = " + id + " was not found.");
	}
	
	@PostMapping("/students/add")
	public ResponseEntity<?> addStudent(@RequestBody Student newStudent){
		
		newStudent.setId(-1);
		
		Student added = service.save(newStudent);
		
		System.out.println("Added: " + added);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	@PutMapping("students/update")
	public ResponseEntity<?> updateStudent(@RequestBody Student updateStudent) throws ResourceNotFoundException {
		//check if student exists, then update them
		
		Optional<Student> found = service.findById(updateStudent.getId());
		
		if(found.isPresent()) {
			Student updated = service.save(updateStudent);
			return ResponseEntity.status(200).body(updated);
		}
		else {
			throw new ResourceNotFoundException("Student", updateStudent.getId());
		}
		
	}
	
	@DeleteMapping("students/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable int id) throws ResourceNotFoundException{
		
		Optional<Student> found = service.findById(id);
		
		if(found.isPresent()) {
			service.deleteById(id);
			
			return ResponseEntity.status(200).body(found.get());
		}
		else {
			throw new ResourceNotFoundException("Student", id);
		}
		
	}
	
	@GetMapping("/students/instructor/{instructorId}")
    public ResponseEntity<?> getStudentsByInstructor(@PathVariable int instructorId) throws ResourceNotFoundException {

        List<Student> students = service.findByInstructorId(instructorId);

        if (!students.isEmpty()) {
            return ResponseEntity.status(200).body(students);
        }
        
        throw new ResourceNotFoundException("Instructor", instructorId);
    }
}
