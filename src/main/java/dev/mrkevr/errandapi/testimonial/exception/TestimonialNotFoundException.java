package dev.mrkevr.errandapi.testimonial.exception;

import dev.mrkevr.errandapi.common.exception.ResourceNotFoundException;

public class TestimonialNotFoundException extends ResourceNotFoundException {

	private static final long serialVersionUID = 1L;

	public TestimonialNotFoundException() {
		super("Testimonial not found");
	}

	public TestimonialNotFoundException(String message) {
		super(message);
	}
}
