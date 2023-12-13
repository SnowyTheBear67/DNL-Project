package com.cognixia.jump.instructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;

@Service
public class InstructorService {

	@Autowired
	InstructorRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	public List<Instructor> getAllInstructors(){
		return repo.findAll();
	}
	
	public Instructor getInstructor(int id) throws ResourceNotFoundException {
		
		Optional<Instructor> found = repo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("Instructor", id);
		}
		
		return found.get();
	}
	
	public Instructor createInstructor(Instructor instructor) {
		
		instructor.setId(null);
		
		// will need to encode the password ourselves before it gets saved to the DB
		// spring security won't know to do this automatically, so we need to make sure it
		// gets done anytime we create a new user
		instructor.setPassword( encoder.encode( instructor.getPassword() ) );
		
		Instructor created = repo.save(instructor);
		
		return created;
	}
	
	public Instructor updateInstructor(Instructor instructor) throws ResourceNotFoundException {
		
		boolean exists = repo.existsById(instructor.getId());
		
		if(exists) {
			Instructor updated = repo.save(instructor);
			return updated;
		}
		
		throw new ResourceNotFoundException("Instructor", instructor.getId());
	}
	
	public boolean deleteInstructor(int id) throws ResourceNotFoundException {
		
		boolean exists = repo.existsById(id);
		
		if(exists) {
			repo.deleteById(id);
			return true;
		}
		
		throw new ResourceNotFoundException("Instructor", id);
	}
}
