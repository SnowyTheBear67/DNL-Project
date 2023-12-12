package com.cognixia.jump.student;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{
  
	public Optional<Student> findById( Integer id );
  
  public List<Student> findAll();
  
}
