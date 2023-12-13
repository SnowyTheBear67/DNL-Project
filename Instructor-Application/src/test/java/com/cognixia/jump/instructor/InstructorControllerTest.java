package com.cognixia.jump.instructor;

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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.jwt.JwtUtil;
import com.cognixia.jump.security.SecurityConfiguration;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.student.Student;

@WebMvcTest(InstructorController.class)
@Import( SecurityConfiguration.class)
//@WithMockUser()
public class InstructorControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MyUserDetailsService userDetailsService;
	
	@MockBean
	private JwtUtil jwt;
	
	@MockBean
	private InstructorRepository repo;
	
	@MockBean
	private InstructorService service;
	
	@InjectMocks
	private InstructorController controller;
	
	
	@Test
	@WithMockUser
	void testGetAllInstructors() throws Exception {
		
		String uri = STARTING_URI + "/instructors";
		
		List<Instructor> allInstructors = new ArrayList<Instructor>();
		allInstructors.add( new Instructor(1, "Peppa", "Pig", "peppig", "securePw", true, Instructor.Role.ROLE_USER));
		//allInstructors.add( new Instructor(2, "Matthew", "Love", "matlove", "password", true, Instructor.Role.ROLE_USER));
		
		when( service.getAllInstructors() ).thenReturn(allInstructors);
		
		mvc.perform( get(uri) )   // perform get request
		.andDo( print() ) // print request sent/response given
		.andExpect( status().isOk() )
		.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE))
		.andExpect( jsonPath("$.length()").value( allInstructors.size() ) )
		.andExpect( jsonPath( "$.length()" ).value( allInstructors.size() ) ) // length of the list matches one above
		.andExpect( jsonPath("$[0].id").value(allInstructors.get(0).getId()) ) // check each column value for the cars in list
		.andExpect( jsonPath("$[0].firstName").value(allInstructors.get(0).getFirstName() ) )
		.andExpect( jsonPath("$[0].lastName").value(allInstructors.get(0).getLastName() ) )
		.andExpect( jsonPath("$[0].username").value(allInstructors.get(0).getUsername() ) )
		.andExpect(jsonPath("$[0].password").value(allInstructors.get(0).getPassword() ) )
		.andExpect(jsonPath("$[0].enabled").value(allInstructors.get(0).isEnabled() ) );
		
		// Check each normalized JSON object in the array
		
		verify( service, times(1) ).getAllInstructors();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testGetInstructor() throws Exception {
		int id = 1;
		String uri = STARTING_URI + "/instructors/{id}";
		
		// instructor that will be returned by service
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "password", true, Instructor.Role.ROLE_USER);
		
		// when service called, return this object
		when( service.getInstructor(id) ).thenReturn(instructor);
		
	    String expectedJson = normalizeJson(instructor.toJson());
		
		mvc.perform( get(uri, id) ) // create get request and pass id to uri path
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) )
				.andExpect(content().json(expectedJson));
		
		verify( service, times(1) ).getInstructor(id);
		verifyNoMoreInteractions(service);
	}
	
	
	//method to normalize the json format of the enum
	private String normalizeJson(String json) {
	    // Trim whitespace and convert to a consistent format
	    return json.trim().replace("\r\n", "\n").replace("\n", "").replace(" ", "");
	}
	
	@Test
	@WithMockUser
	void testGetInstructorNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/instructors/{id}";
		
		// when this method gets called, this time an exception will be thrown
		when( service.getInstructor(id) )
			.thenThrow( new ResourceNotFoundException("Student", id) );
		
		// because of the exception, we're only checking to make sure we got the expected 404 status code
		mvc.perform( get(uri, id) )
			.andDo( print() )
			.andExpect( status().isNotFound() );
		
		// verify the calls made
		verify( service, times(1) ).getInstructor(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testCreateInstructor() throws Exception {
		String uri = STARTING_URI + "/instructors";
		
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "password", true, Instructor.Role.ROLE_USER);
		
		when( service.createInstructor( Mockito.any(Instructor.class) ) ).thenReturn(instructor);
		
		mvc.perform( post(uri)
					.content( instructor.toJson() ) // data sent in body NEEDS to be in JSON format
					.contentType(MediaType.APPLICATION_JSON_VALUE) )
			.andDo( print() )
			.andExpect( status().isCreated() )
			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) );
		
	}
	
	@Test
	@WithMockUser
	void testUpdateInstructor() throws Exception {
		
		String uri = STARTING_URI + "/instructors/update";
		
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "password", true, Instructor.Role.ROLE_USER);

		when( service.updateInstructor(Mockito.any(Instructor.class))).thenReturn(instructor);
		
		mvc.perform( put(uri)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content( instructor.toJson() ) )
					.andExpect(status().isOk());
		
		verify(service, times(1)).updateInstructor(Mockito.any(Instructor.class));
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	@WithMockUser
	void testUpdateInstructorNotFound() throws Exception {
		
		String uri = STARTING_URI + "/instructors/update";
		int id = 1;
		
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "password", true, Instructor.Role.ROLE_USER);
		
		
		when(service.updateInstructor( Mockito.any(Instructor.class)))
			.thenThrow(new ResourceNotFoundException("Instructor", id));
		
		mvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content( instructor.toJson() ) )
				.andExpect(status().isNotFound());
		
		verify(service, times(1)).updateInstructor( Mockito.any(Instructor.class) );
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testDeleteInstructor() throws Exception {
		
		String uri = STARTING_URI + "/instructors/delete/{id}";
		int id = 1;
		
		Instructor instructor = new Instructor(1, "Peppa", "Pig", "peppig", "password", true, Instructor.Role.ROLE_USER);
		
		
		when( service.deleteInstructor(id) ).thenReturn(true);
		
		
		mvc.perform( delete(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() );
				// can do more tests to make sure json data formatted properly
		
		verify( service, times(1) ).deleteInstructor(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	@WithMockUser
	void testDeleteInstructorNotFound() throws Exception {
		
		String uri = STARTING_URI + "/instructors/delete/{id}";
		int id = 1;
		
		when( service.deleteInstructor(id) )
			.thenThrow( new ResourceNotFoundException("Instructor", id) );
		
		
		mvc.perform( delete(uri, id) )
				.andExpect( status().isNotFound() );
		
		
		verify( service, times(1) ).deleteInstructor(id);
		verifyNoMoreInteractions(service);
	}
	
	//getAllInstructors
	//getInstructor
	//createInstructor
	//updateInstructor
	//deleteInstructor
	

	
	
	
}
