package dev.mrkevr.errandapi.library.validator;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !userRepository.existsByEmail(value);
	}
}
