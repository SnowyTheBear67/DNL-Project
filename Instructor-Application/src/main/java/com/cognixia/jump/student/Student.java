package com.cognixia.jump.student;


import java.io.Serializable;
import java.util.Objects;

import com.cognixia.jump.instructor.Instructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	@NotBlank
	private String firstName;
	
	@Column
	@NotBlank
	private String lastName;
	
	@Column
	@NotBlank
	private String email;
	
	@Min(0)
	@Max(100)
	@Column
	private Integer grade;
	
	@Column
	private String jumpClass;
	
	@Column
	private String notes;
	
	@ManyToOne
	@JoinColumn(name = "instructor_id", referencedColumnName = "id")
	private Instructor instructor;

	public Student() {
		
	}
	
	public Student(int id, String firstName, String lastName, String email, int grade, String jumpClass, String notes) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.grade = grade;
		this.jumpClass = jumpClass;
		this.notes = notes;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	

	public String getJumpClass() {
		return jumpClass;
	}

	public void setJumpClass(String jumpClass) {
		this.jumpClass = jumpClass;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", grade=" + grade + ", jumpClass=" + jumpClass + ", notes=" + notes + "]";
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, grade, id, instructor, jumpClass, lastName, notes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(grade, other.grade) && Objects.equals(id, other.id)
				&& Objects.equals(instructor, other.instructor) && Objects.equals(jumpClass, other.jumpClass)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(notes, other.notes);
	}

	public String toJson() {

		return "{\"id\" : " + id 
				+ ", \"firstName\" : \"" + firstName + "\""
				+ ", \"lastName\" : \"" + lastName + "\""
				+ ", \"email\" : \"" + email + "\""
				+ ", \"grade\" : " + grade 
				+ ", \"jumpClass\" : \"" + jumpClass + "\""
				+ ", \"notes\" : \"" + notes + "\"}";
	}
	
	
	
	
}
