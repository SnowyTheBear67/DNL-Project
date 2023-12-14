package com.cognixia.jump.student;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{
  
	public Optional<Student> findById( Integer id );
  
	public List<Student> findAll();
  
	// Custom query to find students by instructor username
	@Query("SELECT s FROM Student s WHERE s.instructor.username = :instructorUsername")
	List<Student> findByInstructorUsername(@Param("instructorUsername") String instructorUsername);
  
  
  
}
