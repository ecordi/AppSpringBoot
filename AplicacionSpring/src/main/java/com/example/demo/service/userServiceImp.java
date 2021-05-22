package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.CustomeFieldValidationException;
import com.example.demo.Exception.UsernameOrIdNotFound;
import com.example.demo.dto.ChangePasswordForm;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class userServiceImp implements UserService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;

	@Override
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
		if (userFound.isPresent()) {
			throw new CustomeFieldValidationException ("Username no disponible","username");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomeFieldValidationException("Password y Confirm Password no son iguales","confirmPassword");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			user = userRepository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws UsernameOrIdNotFound {
		return userRepository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe"));
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN',ROLE_USER)")
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
	public void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")	
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		User user = getUserById(id);
		userRepository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());
		if (!isLoggedUserADMIN() && user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current Password invalid.");
		}
		if (user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("Nuevo debe ser diferente al password actual.");
		}
		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("Nuevo Password y Confirm Password no coinciden.");
		}
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return userRepository.save(user);
	}

	public boolean isLoggedUserADMIN() {
		return loggedUserHasRole("ROLE_ADMIN");
	}

	public boolean loggedUserHasRole(String role) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUser = null;
		Object roles = null;
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			roles = loggedUser.getAuthorities().stream().filter(x -> role.equals(x.getAuthority())).findFirst()
					.orElse(null); // loggedUser = null;
		}
		return roles != null ? true : false;
	}
}