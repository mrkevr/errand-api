package dev.mrkevr.errandapi.common.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

	List<String> allowedFileFormats;
	int minWidth;
	int minHeight;
	int maxWidth;
	int maxHeight;
	long maxFileSize;

	@Override
	public void initialize(ValidImageFile constraintAnnotation) {
		allowedFileFormats = Arrays.asList(constraintAnnotation.value());
		minWidth = constraintAnnotation.minWidth();
		minHeight = constraintAnnotation.minHeight();
		maxWidth = constraintAnnotation.maxWidth();
		maxHeight = constraintAnnotation.maxHeight();
		maxFileSize = constraintAnnotation.size();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		// File format check
		if (value.isEmpty() || !allowedFileFormats.contains(value.getContentType())) {
			return false;
		}
		// Image dimension check
		try {
			BufferedImage bufferedImage = ImageIO.read(value.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if (width < minWidth || width > maxWidth) {
				return false;
			}
			if (height < minHeight || height > maxHeight) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		// File size check (not more than 50kb)
		if (value.getSize() > maxFileSize) {
			return false;
		}
		return true;
	}
}
