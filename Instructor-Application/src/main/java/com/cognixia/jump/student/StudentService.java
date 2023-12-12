package com.cognixia.jump.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	private StudentRepository repo;
	
	public Student getStudentById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
