package dev.mrkevr.errandapi.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.dto.UserResponse;

@Component
public class UserMapper {
	
	private final String BASE_URL = "http://localhost:9001/api/users/";
	
	public UserResponse map(User user) {
		
		UserResponse userResponse = UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.name(user.getName())
			.title(user.getTitle())
			.avatar(BASE_URL + user.getId() + "/avatar")
			.aboutMe(user.getAboutMe())
			.phone(user.getPhone())
			.email(user.getEmail())
			.rating(user.getRating())
			.errandsWorked(user.getErrandsWorked())
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
