package dev.mrkevr.errandapi.testimonial.api;

import dev.mrkevr.errandapi.common.entity.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "testimonials")
@Entity(name = "Testimonial")
public class Testimonial extends GenericEntity {

	static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	String userId;

	@Column(name = "testifier_username")
	String testifierUsername;
	
	@Column(name = "rating")
	int rating;

	@Lob
	@Column(name = "content")
	String content;
}
