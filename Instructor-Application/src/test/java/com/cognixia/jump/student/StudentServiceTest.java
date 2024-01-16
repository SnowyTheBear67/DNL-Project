package com.cognixia.jump.student;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.instructor.Instructor;
import com.cognixia.jump.instructor.InstructorRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentRepository repo;
	
	@InjectMocks
	private StudentService service;
	
	@Mock 
    InstructorRepository instructorRepo;
	
	@Test
	void testGetAllStudents() throws Exception {
		
		List<Student> allStudents = new ArrayList<Student>();
		allStudents.add( new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "")  );
		allStudents.add( new Student(2, "Orquidia", "Moreno", "pepp2@gmail.com", 90, "Oct", "good student") );
		
		when( repo.findAll() ).thenReturn(allStudents);
		
		List<Student> result = service.getAllStudents();
		
		for( int i = 0; i < allStudents.size(); i++ ) {
			
			if( allStudents.get(i).equals( result.get(i) ) ) {
				System.out.println("Equal");
			}
			else {
				fail();
			}	
		}	
	}
	
	@Test
	void testGetStudent() throws Exception {
		int id = 1;
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		when( repo.findById(id) ).thenReturn(Optional.of(student));
		
		Student result = service.getStudent(id);
		
		assertEquals(student, result);
	}
	
	@Test
	void testGetStudentNotFound() throws Exception {
		
		int id = 1;
		
		when( repo.findById(id) ).thenReturn( Optional.empty() );
		
		assertThrows(ResourceNotFoundException.class,  () -> {
			service.getStudent(id);
		});
	}
	
	@Test
    void testAddStudent () throws Exception {
        // Mocking an instructor
        Instructor instructor = new Instructor(1, "John", "Doe", "johndoe", "password", true, Instructor.Role.ROLE_USER);
        when(instructorRepo.findById(any())).thenReturn(Optional.of(instructor));
        
        Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "notes");
        student.setInstructor(instructor); // Setting the instructor ID

        
        when( repo.save(student) ).thenReturn(student);
        
        Student result = service.addStudent(student);
        
        verify(repo).save(student);
        
        assertEquals(student, result);
    }
	@Test
	void testUpdateStudent() throws Exception {
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		when( repo.existsById(student.getId()) ).thenReturn(true);
		when( repo.save(Mockito.any()) ).thenReturn(student);
		
		Student created = service.updateStudent(student);
		
		assertEquals(student, created);
	}
	
	@Test
	void testUpdateStudentNotFound() throws Exception {
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		when( repo.existsById(student.getId()) ).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.updateStudent(student);
		});
	}
	
	@Test
	void testDeleteStudent() throws Exception {
		
		int id = 1;
		
		when(repo.existsById(id)).thenReturn(true);
		
		boolean deleted = service.deleteStudent(id);
		
		assertTrue(deleted);
	}
	
	@Test
	void testDeleteStudentNotFound() throws Exception {
		
		int id = 1;
		
		when(repo.existsById(id)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteStudent(id);
		});
	}
}
