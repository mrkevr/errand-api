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
	
	String title;

	String avatar;

	String email;

	String phone;

	String aboutMe;

	int rating;
	
	int errandsWorked;

	LocalDateTime created;

	LocalDateTime modified;
}
