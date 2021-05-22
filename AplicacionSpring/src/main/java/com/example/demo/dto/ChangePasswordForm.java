package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordForm {
@NotNull
private Long id;
@NotBlank(message="Current password must not be blank")
private String currentPassword;
@NotBlank(message="New password must not be blank")
private String newPassword;
@NotBlank(message="Confirm password must not be blank")
private String confirmPassword;

public ChangePasswordForm() {
	super();
}

public ChangePasswordForm(@NotNull Long id) {
	this.id = id;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getCurrentPassword() {
	return currentPassword;
}

public void setCurrentPassword(String currentPassword) {
	this.currentPassword = currentPassword;
}

public String getNewPassword() {
	return newPassword;
}

public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}

public String getConfirmPassword() {
	return confirmPassword;
}

public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}

}
