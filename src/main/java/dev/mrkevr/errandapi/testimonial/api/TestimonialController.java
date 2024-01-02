package dev.mrkevr.errandapi.testimonial.api;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.mrkevr.errandapi.common.dto.ResponseEntityBody;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialCreationRequest;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TestimonialController {
	
	TestimonialService testimonialService;
	
	/*
	 * Return all the testimonials by user id
	 */
	@GetMapping
	ResponseEntity<ResponseEntityBody> getAllByUserId(
			@RequestParam(required = true) String userId,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "1000") int size) {
		
		List<TestimonialResponse> testimonials = testimonialService.getAllByUserId(userId, page, size);
		String title = "Testimonials about user " + userId;
		Map<String, Object> body = new HashMap<>();
		body.put("page", page);
		body.put("size", size);
		body.put("testimonials", testimonials);
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, body);
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ResponseEntityBody> getById(
			@PathVariable 
			String id) {
		
		TestimonialResponse testimonial = testimonialService.getById(id);
		String title = "Testimonial found successfully";
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, Map.of("user", testimonial));
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@PostMapping
	ResponseEntity<ResponseEntityBody> save(
			@Valid 
			@RequestBody(required = true) 
			TestimonialCreationRequest testimonialCreationRequest) {
		
		TestimonialResponse testimonialResponse = testimonialService.add(testimonialCreationRequest);
		String title = "Testimonial is successfully saved";
		String uri = "/api/testimonials/" + testimonialResponse.getId();
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.CREATED, Map.of("savedTestimonial", testimonialResponse));
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
}
