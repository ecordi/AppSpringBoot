package com.example.demo.service;

import java.util.Optional;

import org.hibernate.loader.plan.exec.process.internal.AbstractRowReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ChangePasswordForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class userServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new Exception("Username no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Password y Confirm Password no son iguales");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
<<<<<<< HEAD
		return userRepository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
=======
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
>>>>>>> branch 'main' of https://github.com/ecordi/AppSpringBoot.git
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}

	/**
	 * Map everythin but the password.
	 * 
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
<<<<<<< HEAD

	public void deleteUser(Long id) throws Exception {
		User user = getUserById(id);
		userRepository.delete(user);

	}

	public User changePassword(ChangePasswordForm form) throws Exception {
		User storedUser = userRepository.findById(form.getId())
				.orElseThrow(() -> new Exception("UsernotFound in ChangePassword -" + this.getClass().getName()));
		if (!storedUser.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current password Invalid");
		}
		if (storedUser.getPassword().equals(form.getNewPassword())) {
			throw new Exception("New passwor must be different to current");
		}
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("New password must the same to confirm password");
		}
		storedUser.setPassword(form.getNewPassword());
		return userRepository.save(storedUser);
=======
	
	public void deleteUser(Long id) throws Exception{
		User user = getUserById(id);
		repository.delete(user);
>>>>>>> branch 'main' of https://github.com/ecordi/AppSpringBoot.git
		
	}
}