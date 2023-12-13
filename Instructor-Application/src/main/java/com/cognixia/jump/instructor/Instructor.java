package com.cognixia.jump.instructor;

import java.io.Serializable;
import java.util.List;

import com.cognixia.jump.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Instructor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static enum Role {
		ROLE_USER, ROLE_ADMIN // roles need to start with ROLE_ & be all caps b/c security will look for this naming structure
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	@NotBlank
	private String firstName;
	
	@Column
	@NotBlank
	private String lastName;
	
	@Column(unique = true, nullable = false)
	@NotBlank
	private String username;
	
	@Column(nullable = false)
	@NotBlank
	private String password;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Student> students;

	public Instructor() {
		
	}
	
	public Instructor(Integer id, String first_name, String last_name, String username, String password, boolean enabled, Role role) {
		super();
		this.id = id;
		this.firstName = first_name;
		this.lastName = last_name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String toJson() {

		return "{\"id\" : " + id 
				+ ", \"firstName\" : \"" + firstName + "\""
				+ ", \"lastName\" : \"" + lastName + "\""
				+ ", \"username\" : \"" + username + "\""
				+ ", \"password\" : \"" + password + "\"" 
				+ ", \"enabled\" : " + enabled 
				+ ", \"role\" : \"" + role + "\"}";
	}
	
}
