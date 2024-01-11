package dev.mrkevr.errandapi.errand.api;

import dev.mrkevr.errandapi.common.entity.GenericEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "errands")
@Entity(name = "Errand")
public class Errand extends GenericEntity {

	private static final long serialVersionUID = 1L;
	
	// User id of the one who posted the errand
	@Column(name = "employer_username")
	String employerUsername;

	// User id of the one who is running the errand, initial value is null
	@Column(name = "agent_username")
	String agentUsername;

	@Column(name = "title")
	String title;

	@Lob
	@Column(name = "description")
	String description;
	
	@Column(name = "image_url")
	String imageUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "errand_category")
	ErrandCategory errandCategory;
	
	@Embedded
	@AttributeOverrides({ 
		@AttributeOverride(name = "address", column = @Column(name = "address")),
		@AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
		@AttributeOverride(name = "longitude", column = @Column(name = "longitude")) })
	ErrandLocation errandLocation;
	
	@Column(name = "compensation")
	double compensation;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "errand_status")
	ErrandStatus errandStatus;
}
