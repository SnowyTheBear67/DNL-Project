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
	
	public List<Student> getStudentsByInstructorUsername (String username) throws ResourceNotFoundException {
		
		Optional<Instructor> found = instructorRepo.findByUsername(username);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Instructor", username);
		}
		List<Student> students = repo.findByInstructorUsername(username);
		
		return students;
	}
	
	public Student addStudent(Student student) throws ResourceNotFoundException {
        student.setId(null);
        
        // Fetch the instructor using the provided instructorId
        Optional<Instructor> instructorOptional = instructorRepo.findById(student.getInstructor().getId());
        
        //Student added = repo.save(student);
        
        if (instructorOptional.isPresent()) {
            // Set the fetched instructor in the student object
            Instructor instructor = instructorOptional.get();
            student.setInstructor(instructor);
            
            // Save the student with the associated instructor
            Student added = repo.save(student);
            
            return added;
        } else {
            throw new ResourceNotFoundException("Instructor", student.getInstructor().getId());
        }
        
        
    }
}
