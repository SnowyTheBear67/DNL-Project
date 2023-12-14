package com.cognixia.jump.exception;

public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String resource, int id) {
		super(resource + " with id = " + id  + " was not found");
	}

	public ResourceNotFoundException(String resource, String string) {
		super(resource + " with username = " + string  + " was not found");
	}

}
