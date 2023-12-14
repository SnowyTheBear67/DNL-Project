package com.cognixia.jump.student;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.instructor.Instructor;
import com.cognixia.jump.instructor.InstructorRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository repo;
	
	@Autowired
	InstructorRepository instructorRepo;
	
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
	
	public List<Student> getStudentsByInstructorId (String username) throws ResourceNotFoundException {
		
		Optional<Instructor> found = instructorRepo.findByUsername(username);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Instructor", username);
		}
		List<Student> students = repo.findByInstructorUsername(username);
		
		return students;
	}
	
	
}
