package dev.mrkevr.errandapi.user.api;

import dev.mrkevr.errandapi.common.entity.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@ToString()
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
@Entity(name = "User")
public class User extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "username")
	String username;

	@Column(name = "name")
	String name;
	
	@Column(name = "title")
	String title;

	@Column(name = "password")
	String password;

	@Column(name = "avatar_url")
	String avatarUrl;

	@Column(name = "email")
	String email;

	@Column(name = "phone")
	String phone;

	@Column(name = "about_me")
	String aboutMe;

	@Column(name = "rating")
	int rating;
	
	@Column(name = "errands_worked")
	int errandsWorked;
	
	@Override
	public String getIdPrefix() {
		return "USER";
	}
}
