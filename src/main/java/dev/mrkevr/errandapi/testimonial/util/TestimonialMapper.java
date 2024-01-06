package dev.mrkevr.errandapi.testimonial.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.testimonial.api.Testimonial;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialResponse;

@Component
public class TestimonialMapper {

	public TestimonialResponse map(Testimonial testimonial) {
		TestimonialResponse response = TestimonialResponse.builder()
			.id(testimonial.getId())
			.userId(testimonial.getUserId())
			.testifierUsername(testimonial.getTestifierUsername())
			.rating(testimonial.getRating())
			.content(testimonial.getContent())
			.posted(testimonial.getCreated())
			.build();
		return response;
	}

	public List<TestimonialResponse> map(List<Testimonial> testimonials) {
		return testimonials.stream().map(testimonial -> map(testimonial)).collect(Collectors.toList());
	}
}
