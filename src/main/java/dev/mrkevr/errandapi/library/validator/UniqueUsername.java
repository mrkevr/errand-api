package dev.mrkevr.errandapi.library.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface UniqueUsername {
	
	public String message() default "Username already exists.";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};
}