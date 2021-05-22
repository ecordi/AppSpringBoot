package com.example.demo.service;

import com.example.demo.Exception.UsernameOrIdNotFound;
import com.example.demo.dto.ChangePasswordForm;
import com.example.demo.entity.User;

public interface UserService {

	public Iterable<User> getAllUsers();
	public User createUser(User user) throws Exception;
	public User getUserById(Long id) throws UsernameOrIdNotFound;
    public User updateUser(User user) throws Exception;        
    public void deleteUser(Long id) throws UsernameOrIdNotFound;        
    public User changePassword(ChangePasswordForm form) throws Exception;


}
