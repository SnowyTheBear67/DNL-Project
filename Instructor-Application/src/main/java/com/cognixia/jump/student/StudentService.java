package com.cognixia.jump.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;

@Service
public class StudentService {

	@Autowired
	StudentRepository repo;
	
	public List<Student> getAllStudents() {
		return repo.findAll();
	}
	
	public Student getStudent(int id) throws ResourceNotFoundException {
		
		Optional<Student> found = repo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Student", id);
		}
		
		return found.get();
	}
	
	public Student addStudent(Student student) {
		student.setId(null);
		
		Student added = repo.save(student);
		
		return added;
	}
	
	public Student updateStudent(Student student) throws ResourceNotFoundException{ 
		boolean exists = repo.existsById(student.getId());
		
		if(exists) {
			Student updated = repo.save(student);
			
			return updated;
		}
		
		throw new ResourceNotFoundException("Student", student.getId());
	}
	
	public boolean deleteStudent(int id) throws ResourceNotFoundException {
		boolean exists = repo.existsById(id);
		
		if(exists) {
			repo.deleteById(id);
			
			return true;
		}
		
		throw new ResourceNotFoundException("Student", id);
	}
	
	public List<Student> getStudentsByInstructorId (int instructorId) {
		
		List<Student> students = repo.findByInstructorId(instructorId);
		
		return students;
	}
	
	
}
