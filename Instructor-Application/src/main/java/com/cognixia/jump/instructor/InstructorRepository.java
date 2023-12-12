package com.cognixia.jump.instructor;

import java.util.Optional;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Integer>{
	
	public Optional<Instructor>
	findByUsername( String username );
  
  public List<Instructor> findAll();

}
