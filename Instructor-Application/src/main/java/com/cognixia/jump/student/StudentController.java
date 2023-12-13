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
	StudentService service;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return service.getAllStudents();
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable int id) throws ResourceNotFoundException {
		
		Student student = service.getStudent(id);
		
		return ResponseEntity.status(200).body(student);
	}
	
	@PostMapping("/students/add")
	public ResponseEntity<?> addStudent(@RequestBody Student newStudent){
		
		Student added = service.addStudent(newStudent);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	@PutMapping("students/update")
	public ResponseEntity<?> updateStudent(@RequestBody Student updateStudent) throws ResourceNotFoundException {
		
		Student updated = service.updateStudent(updateStudent);
		
		return ResponseEntity.status(200).body(updateStudent);
		
	}
	
	@DeleteMapping("students/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable int id) throws ResourceNotFoundException{
		
		service.deleteStudent(id);
		
		return ResponseEntity.status(200).body("Deleted Student with id = " + id);
		
	}
	
	@GetMapping("/students/instructor/{instructorId}")
    public ResponseEntity<?> getStudentsByInstructorId(@PathVariable int instructorId) throws ResourceNotFoundException {

		List<Student> students = service.getStudentsByInstructorId(instructorId);

        if (!students.isEmpty()) {
            return ResponseEntity.status(200).body(students);
        }
        
        throw new ResourceNotFoundException("Instructor", instructorId);
    }
}
