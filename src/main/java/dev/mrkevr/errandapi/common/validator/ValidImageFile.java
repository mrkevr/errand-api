package dev.mrkevr.errandapi.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ValidImageFileValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface ValidImageFile {

	String[] value() default { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE };

	int minWidth() default 150;
	
	int minHeight() default 150;
	
	int maxWidth() default 150;
	
	int maxHeight() default 150;
	
	long size() default 100_000L;

	String message() default "Please upload a valid image file(png/jpg, 100kb or less, 150x150px)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
