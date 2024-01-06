package dev.mrkevr.errandapi.user.dto;

import dev.mrkevr.errandapi.common.validator.UniqueEmail;
import dev.mrkevr.errandapi.common.validator.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
	
	@UniqueUsername
	@NotEmpty(message = "Field `username` must not be empty")
	@Pattern(regexp = "^(?=.{6,24}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Invalid `username` format")
	String username;

	@NotEmpty(message = "Field `password` must not be empty")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Field `password` must contain at least eight characters, at least one number and both lower and uppercase letters and special characters")
	String password;

	@NotEmpty(message = "Field `name` must not be empty")
	@Size(min = 6, max = 120, message = "Field `name` must be 6-120 chatacters")
	String name;
	
	@NotEmpty(message = "Field `title` must not be empty")
	@Size(min = 6, max = 24, message = "Field `title` must be 6-120 chatacters")
	String title;
	
	@UniqueEmail
	@NotBlank(message = "Field `email` must not be blank")
	@Email(message = "Invalid `email` format")
	String email;

	@NotBlank(message = "Field `email` must not be blank")
	@Size(min = 8, max = 24, message = "Field `phone` must be 8-24 chatacters")
	String phone;

	@NotEmpty(message = "Field `aboutMe` must not be empty")
	@Size(min = 12, max = 300, message = "Field `aboutMe` must be 12-300 chatacters")
	String aboutMe;
}
