package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class userController {

	@GetMapping("/home")
	public String index(){
		return "index";
	}
	
	@GetMapping("/userForm")
	public String userf(){
		return "user-form/user-view";
	}
	
	
}
