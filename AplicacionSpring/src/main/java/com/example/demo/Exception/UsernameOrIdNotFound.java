package com.example.demo.Exception;

public class UsernameOrIdNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5147619710130574147L;

	public UsernameOrIdNotFound() {
		super("Usuario o Id no encontrado");
	}
	
	public UsernameOrIdNotFound(String message) {
		super(message);
	}
}
