package dev.mrkevr.errandapi.testimonial.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestimonialResponse {
	
	String id;
	
	String userId;

	String testifierId;

	String content;
	
	LocalDateTime posted;
}
