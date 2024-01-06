package dev.mrkevr.errandapi.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.testimonial.api.TestimonialService;
import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final TestimonialService testimonialService;
	
	public UserResponse map(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.name(user.getName())
			.title(user.getTitle())
			.avatarUrl(user.getAvatarUrl())
			.aboutMe(user.getAboutMe())
			.rating(testimonialService.getAverageRatingByUserId(user.getId()))
			.phone(user.getPhone())
			.email(user.getEmail())
			.errandsWorked(user.getErrandsWorked())
			.joined(user.getCreated().toLocalDate())
			.build();
	}

	public List<UserResponse> map(List<User> users) {
		return users.stream()
			.map(user -> map(user))
			.collect(Collectors.toList());
	}
}
