package dev.mrkevr.errandapi.common.validator;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.api.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	private final UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !userRepository.existsByUsername(value);
	}
}