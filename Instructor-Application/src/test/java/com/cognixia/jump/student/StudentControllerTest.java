package com.cognixia.jump.student;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.instructor.InstructorRepository;
import com.cognixia.jump.jwt.JwtUtil;
import com.cognixia.jump.security.SecurityConfiguration;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.student.Student;
import com.cognixia.jump.student.StudentController;
import com.cognixia.jump.student.StudentService;

@WebMvcTest(StudentController.class)
@Import( SecurityConfiguration.class)
public class StudentControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MyUserDetailsService userDetailsService;
	
	@MockBean
	private JwtUtil jwt;
	
	@MockBean
	private StudentRepository repo;
	
	@MockBean
	private InstructorRepository instRepo;
	
	@MockBean
	private StudentService service;
	
	@InjectMocks
	private StudentController controller;
	
	@Test
	@WithMockUser
	void testGetAllStudents() throws Exception {
		
		String uri = STARTING_URI + "/students";
		
		List<Student> allStudents = new ArrayList<Student>();
		allStudents.add( new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", ""));
		allStudents.add( new Student(2, "Orquidia", "Moreno", "pepp2@gmail.com", 90, "Oct", "good student"));
		
		when( service.getAllStudents() ).thenReturn(allStudents);
		
		mvc.perform( get(uri) )   // perform get request
		.andDo( print() ) // print request sent/response given
		.andExpect( status().isOk() )
		.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE))
		.andExpect( jsonPath( "$.length()" ).value( allStudents.size() ) )
		.andExpect( jsonPath("$[0].id").value(allStudents.get(0).getId()) ) // check each column value for the cars in list
		.andExpect( jsonPath("$[0].firstName").value(allStudents.get(0).getFirstName() ) )
		.andExpect( jsonPath("$[0].lastName").value(allStudents.get(0).getLastName() ) )
		.andExpect( jsonPath("$[0].email").value(allStudents.get(0).getEmail() ) )
		.andExpect(jsonPath("$[0].grade").value(allStudents.get(0).getGrade() ) )
		.andExpect(jsonPath("$[0].jumpClass").value(allStudents.get(0).getJumpClass() ) )
		.andExpect(jsonPath("$[0].notes").value(allStudents.get(0).getNotes()))
		.andExpect( jsonPath("$[1].id").value(allStudents.get(1).getId()) ) // check each column value for the cars in list
		.andExpect( jsonPath("$[1].firstName").value(allStudents.get(1).getFirstName() ) )
		.andExpect( jsonPath("$[1].lastName").value(allStudents.get(1).getLastName() ) )
		.andExpect( jsonPath("$[1].email").value(allStudents.get(1).getEmail() ) )
		.andExpect(jsonPath("$[1].grade").value(allStudents.get(1).getGrade() ) )
		.andExpect(jsonPath("$[1].jumpClass").value(allStudents.get(1).getJumpClass() ) )
		.andExpect(jsonPath("$[1].notes").value(allStudents.get(1).getNotes()));
		
		verify( service, times(1) ).getAllStudents();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testGetStudent() throws Exception {
		int id = 1;
		String uri = STARTING_URI + "/students/{id}";
		
		// student that will be returned by service
		Student student = new Student(id, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		// when service called, return this car object
		when( service.getStudent(id) ).thenReturn(student);
		
		mvc.perform( get(uri, id) ) // create get request and pass id to uri path
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) )
				.andExpect( jsonPath("$.id").value(student.getId()) ) 
				.andExpect( jsonPath("$.firstName").value(student.getFirstName() ) )
				.andExpect( jsonPath("$.lastName").value(student.getLastName() ) )
				.andExpect( jsonPath("$.email").value(student.getEmail() ) );
		
		verify( service, times(1) ).getStudent(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testGetStudentNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/students/{id}";
		
		// when this method gets called, this time an exception will be thrown
		when( service.getStudent(id) )
			.thenThrow( new ResourceNotFoundException("Student", id) );
		
		// because of the exception, we're only checking to make sure we got the expected 404 status code
		mvc.perform( get(uri, id) )
			.andDo( print() )
			.andExpect( status().isNotFound() );
		
		// verify the calls made
		verify( service, times(1) ).getStudent(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testAddStudent() throws Exception {
		
		String uri = STARTING_URI + "/students/add";
		
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		// when we attempt to add the student, we don't care what kind of student was sent
		// through the request or to the service, what we care about is the student returned
		// as a response
		when( service.addStudent( Mockito.any(Student.class) ) ).thenReturn(student);
		
		
		// NOTE: The toJson() method used below is one we created and located as the last method in the
		//		 Student model file. In order to send our object in the body, we must convert it to JSON,
		//		 so this method takes care to do that. Can also use asJsonString() at the bottom of the file
		//		 here as an alternative if it doesn't work.
		
		mvc.perform( post(uri)
					.content( student.toJson() ) // data sent in body NEEDS to be in JSON format
					.contentType(MediaType.APPLICATION_JSON_VALUE) )
			.andDo( print() )
			.andExpect( status().isCreated() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) );
		
	}
	
	@Test
	@WithMockUser
	void testUpdateStudent() throws Exception {
		
		String uri = STARTING_URI + "/students/update";
		
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		
		when( service.updateStudent( Mockito.any(Student.class) ) ).thenReturn(student);
		
		mvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content( student.toJson() ) )
				.andExpect(status().isOk());
		
		verify(service, times(1)).updateStudent(Mockito.any(Student.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testUpdateStudentNotFound() throws Exception {
		
		String uri = STARTING_URI + "/students/update";
		int id = 1;
		
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		
		when(service.updateStudent( Mockito.any(Student.class)))
			.thenThrow(new ResourceNotFoundException("Student", id));
		
		mvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content( student.toJson() ) )
				.andExpect(status().isNotFound());
		
		verify(service, times(1)).updateStudent( Mockito.any(Student.class) );
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testDeleteStudent() throws Exception {
		
		String uri = STARTING_URI + "/students/delete/{id}";
		int id = 1;
		
		Student student = new Student(1, "Peppa", "Pig", "peppa@gmail.com", 85, "Oct", "");
		
		
		when( service.deleteStudent(id) ).thenReturn(true);
		
		
		mvc.perform( delete(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() );
				// can do more tests to make sure json data formatted properly
		
		verify( service, times(1) ).deleteStudent(id);
		verifyNoMoreInteractions(service);
	}
	
	
	@Test
	@WithMockUser
	void testDeleteStudentNotFound() throws Exception {
		
		String uri = STARTING_URI + "/students/delete/{id}";
		int id = 1;
		
		when( service.deleteStudent(id) )
			.thenThrow( new ResourceNotFoundException("Student", id) );
		
		
		mvc.perform( delete(uri, id) )
				.andExpect( status().isNotFound() );
		
		
		verify( service, times(1) ).deleteStudent(id);
		verifyNoMoreInteractions(service);
	}
	
	//getStudentsByInstructorId

}
