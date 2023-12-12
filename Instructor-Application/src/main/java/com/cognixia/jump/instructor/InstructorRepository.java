package com.cognixia.jump.instructor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
	
<<<<<<< Updated upstream
=======
	public Optional<Instructor>
	findByUsername( String username );
  
>>>>>>> Stashed changes
	public List<Instructor> findAll();

}
