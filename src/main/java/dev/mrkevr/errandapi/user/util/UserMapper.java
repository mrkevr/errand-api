package dev.mrkevr.errandapi.user.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.user.api.User;
import dev.mrkevr.errandapi.user.dto.UserCreationRequest;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
	
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * Create a new User from UserCreationRequest's values
	 */
	public User map(UserCreationRequest userCreationRequest) {
		User user = User.builder()
			.username(userCreationRequest.getUsername())
			.password(passwordEncoder.encode(userCreationRequest.getPassword()))
			.name(userCreationRequest.getName())
			.title(userCreationRequest.getTitle())
			.phone(userCreationRequest.getPhone())
			.email(userCreationRequest.getEmail())
			.aboutMe(userCreationRequest.getAboutMe())
			.averageRating(0)
			.build();
		return user;
	}
	
	public UserResponse map(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.name(user.getName())
			.title(user.getTitle())
			.avatarUrl(user.getAvatarUrl())
			.aboutMe(user.getAboutMe())
			.averageRating(user.getAverageRating())
			.phone(user.getPhone())
			.email(user.getEmail())
			.joined(user.getCreated().toLocalDate())
			.build();
	}

	public List<UserResponse> map(List<User> users) {
		return users.stream()
			.map(user -> map(user))
			.collect(Collectors.toList());
	}
}
