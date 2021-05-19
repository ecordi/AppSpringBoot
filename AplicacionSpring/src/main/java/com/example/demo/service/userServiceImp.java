package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class userServiceImp implements UserService{

	@Autowired
	UserRepository repoisitory;
	
	@Override
	public Iterable<User> getAllUsers() {	
		return repoisitory.findAll();
				}
}
