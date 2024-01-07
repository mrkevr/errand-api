package dev.mrkevr.errandapi.testimonial.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.errandapi.testimonial.dto.TestimonialCreationRequest;
import dev.mrkevr.errandapi.testimonial.dto.TestimonialResponse;
import dev.mrkevr.errandapi.testimonial.exception.TestimonialNotFoundException;
import dev.mrkevr.errandapi.testimonial.util.TestimonialMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestimonialService {
	
	TestimonialRepository testimonialRepository;
	TestimonialMapper testimonialMapper;
	
	public List<TestimonialResponse> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Testimonial> testimonials = testimonialRepository.findAll(pageRequest);
		return testimonialMapper.map(testimonials.getContent());
	}
	
	public List<TestimonialResponse> getAllByUserId(String userId) {
		 List<Testimonial> testimonials = testimonialRepository.findByUserId(userId);
		return testimonialMapper.map(testimonials);
	}
	
	public List<TestimonialResponse> getAllByUserId(String userId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Testimonial> testimonials = testimonialRepository.findByUserId(userId, pageRequest);
		return testimonialMapper.map(testimonials.getContent());
	}
	
	public TestimonialResponse getById(String id) {
		Testimonial testimonial = testimonialRepository.findById(id)
				.orElseThrow(() -> new TestimonialNotFoundException("Could not found testimonial with that id"));
		return testimonialMapper.map(testimonial);
	}
	
	public int getAverageRatingByUserId(String userId) {
		Optional<Double> optional = testimonialRepository.getAverageRatingByUserId(userId);
		
		return optional.isPresent() ? optional.get().intValue() : 0;
	}
	
	@Transactional
	public TestimonialResponse add(TestimonialCreationRequest testimonialCreationRequest) {
		
		Testimonial testimonial = Testimonial.builder()
				.userId(testimonialCreationRequest.getUserId())
				.testifierUsername(testimonialCreationRequest.getTestifierUsername())
				.rating(testimonialCreationRequest.getRating())
				.content(testimonialCreationRequest.getContent())
				.build();
		
		Testimonial savedTestimonial = testimonialRepository.save(testimonial);
		return testimonialMapper.map(savedTestimonial);
	}
}
