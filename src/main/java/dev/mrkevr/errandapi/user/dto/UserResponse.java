package dev.mrkevr.errandapi.user.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

	String id;

	String username;

	String name;

	String avatar;

	String email;

	String phone;

	String aboutMe;

	int rating;

	LocalDateTime created;

	LocalDateTime modified;
}
