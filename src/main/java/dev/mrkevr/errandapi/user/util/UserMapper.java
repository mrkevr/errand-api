package dev.mrkevr.errandapi.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.dto.UserResponse;

@Component
public class UserMapper {
	
	private final String BASE_URL = "http://localhost:9001";
	
	public UserResponse map(User user) {
		
		UserResponse userResponse = UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.name(user.getName())
			.avatar(BASE_URL + "/users/" + user.getId() + "/avatar")
			.aboutMe(user.getAboutMe())
			.phone(user.getPhone())
			.email(user.getEmail())
			.rating(user.getRating())
			.created(user.getCreated())
			.modified(user.getModified())
			.build();
			
		return userResponse;
	}

	public List<UserResponse> map(List<User> users) {
		return users.stream()
				.map(user -> map(user))
				.collect(Collectors.toList());
	}
}
