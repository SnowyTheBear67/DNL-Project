package com.cognixia.jump.instructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.student.Student;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {

	@Mock
	private InstructorRepository repo;
	
	@Mock
	PasswordEncoder encoder;
	
	@InjectMocks
	private InstructorService service;
	
	@Test
	void testGetAllInstructors() throws Exception {
		
		List<Instructor> allInstructors = new ArrayList<Instructor>();
		allInstructors.add( new Instructor(1, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER));
		//allInstructors.add( new Student(2, "Orquidia", "Moreno", "pepp2@gmail.com", 90, "Oct", "good student") );
		
		when( repo.findAll() ).thenReturn(allInstructors);
		
		List<Instructor> result = service.getAllInstructors();
		
		for( int i = 0; i < allInstructors.size(); i++ ) {
			
			if( allInstructors.get(i).equals( result.get(i) ) ) {
				System.out.println("Equal");
			}
			else {
				fail();
			}	
		}	
	}
	
	@Test
	void testGetInstructor() throws Exception {
		int id = 1;
		Instructor instructor = new Instructor(id, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER);
		
		when( repo.findById(id) ).thenReturn(Optional.of(instructor));
		
		Instructor result = service.getInstructor(id);
		
		assertEquals(instructor, result);
	}
	
	@Test
	void testGetInstructorNotFound() throws Exception {
		
		int id = 1;
		
		when( repo.findById(id) ).thenReturn( Optional.empty() );
		
		assertThrows(ResourceNotFoundException.class,  () -> {
			service.getInstructor(id);
		});
	}
	
	@Test
	void testAddInstructor () throws Exception {
		
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER);
		
		when( repo.save(instructor) ).thenReturn(instructor);
		
		Instructor result = service.createInstructor(instructor);
		
		assertEquals(instructor, result);
	}
	
	@Test
	void testUpdateInstructor() throws Exception {
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER);
		
		when( repo.existsById(instructor.getId()) ).thenReturn(true);
		when( repo.save(Mockito.any()) ).thenReturn(instructor);
		
		Instructor created = service.updateInstructor(instructor);
		
		assertEquals(instructor, created);
	}
	
	@Test
	void testUpdateInstructorNotFound() throws Exception {
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER);
		
		when( repo.existsById(instructor.getId()) ).thenReturn(false);
		
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.updateInstructor(instructor);
		});
	}
	
	@Test
	void testDeleteInstructor() throws Exception {
		
		int id = 1;
		
		when(repo.existsById(id)).thenReturn(true);
		
		boolean deleted = service.deleteInstructor(id);
		
		assertTrue(deleted);
	}
	
	@Test
	void testDeleteInstructorNotFound() throws Exception {
		
		int id = 1;
		
		when(repo.existsById(id)).thenReturn(false);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteInstructor(id);
		});
	}
}
