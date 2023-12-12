package com.cognixia.jump.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< Updated upstream
public interface StudentRepository extends JpaRepository<Student, Integer> {

	
	public List<Student> findAll();
=======
public interface StudentRepository extends JpaRepository<Student,Integer>{
  
public Optional<Student> findById( Integer id );
  
  public List<Student> findAll();
  
  //Custom query to find students by instructor ID
  @Query("SELECT s FROM Student s WHERE s.instructor.id = :instructorId")
  List<Student> findByInstructorId(@Param("instructorId") Integer instructorId);
  
>>>>>>> Stashed changes
}
