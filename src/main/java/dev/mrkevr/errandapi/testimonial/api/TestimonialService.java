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
import dev.mrkevr.errandapi.user.api.UserService;
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
	UserService userService;
	
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
		
		/*
		 * Check if an existing Testimonial exists, User can only have one Testimonial for every User
		 */
		Optional<Testimonial> optionalTestmonial = testimonialRepository.findByUserIdAndTestifierUsername(
				testimonialCreationRequest.getUserId(), 
				testimonialCreationRequest.getTestifierUsername());
		
		Testimonial savedTestimonial = null;
		/*
		 * Update Testimonial if it exists, else create new
		 */
		if (optionalTestmonial.isPresent()) {
			Testimonial testimonialToUpdate = optionalTestmonial.get();
			testimonialToUpdate.setRating(testimonialCreationRequest.getRating());
			testimonialToUpdate.setContent(testimonialCreationRequest.getContent());
			
			savedTestimonial = testimonialRepository.save(testimonialToUpdate);
		} else {
			Testimonial testimonialToSave = Testimonial.builder()
					.userId(testimonialCreationRequest.getUserId())
					.testifierUsername(testimonialCreationRequest.getTestifierUsername())
					.rating(testimonialCreationRequest.getRating())
					.content(testimonialCreationRequest.getContent())
					.build();
			
			savedTestimonial = testimonialRepository.save(testimonialToSave);
		}
		
		// Update the User's average rating
		Optional<Double> averageRatingByUserId = testimonialRepository.getAverageRatingByUserId(savedTestimonial.getUserId());
		int newAverageRating = averageRatingByUserId.get().intValue();
		userService.updateAverageRating(savedTestimonial.getUserId(), newAverageRating);
		
		return testimonialMapper.map(savedTestimonial);
	}
}
