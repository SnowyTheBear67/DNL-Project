package com.cognixia.jump.instructor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer>{
	
	public Optional<Instructor>
	findByUsername( String username );
	
}
