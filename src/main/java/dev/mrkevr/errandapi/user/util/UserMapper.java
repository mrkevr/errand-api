package dev.mrkevr.errandapi.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.dto.UserResponse;

@Component
public class UserMapper {
	
	public UserResponse map(User user) {
		UserResponse userResponse = UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.name(user.getName())
			.title(user.getTitle())
			.avatarUrl(user.getAvatarUrl())
			.aboutMe(user.getAboutMe())
			.phone(user.getPhone())
			.email(user.getEmail())
				.rating(user.getRateScore() / user.getTimesRated())
			.errandsWorked(user.getErrandsWorked())
			.joined(user.getCreated().toLocalDate())
			.build();
		return userResponse;
	}

	public List<UserResponse> map(List<User> users) {
		return users.stream()
				.map(user -> map(user))
				.collect(Collectors.toList());
	}
}
