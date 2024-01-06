package dev.mrkevr.errandapi.testimonial.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestimonialCreationRequest {

	@NotEmpty(message = "Field `userId` must not be empty")
	@Size(min = 10, max = 12, message = "Field `userId` must be 10-12 chatacters")
	String userId;

	@NotEmpty(message = "Field `testifierId` must not be empty")
	@Size(min = 10, max = 12, message = "Field `testifierId` must be 10-12 chatacters")
	String testifierUsername;
	
	@NotNull(message = "Field `rating` must not be empty")
	@Min(value = 1, message = "Field `rating` must be between 1-5")
	@Max(value = 1, message = "Field `rating` must be between 1-5")
	int rating;

	@NotEmpty(message = "Field `content` must not be empty")
	@Size(min = 12, max = 120, message = "Field `content` must be 6-120 chatacters")
	String content;
}
