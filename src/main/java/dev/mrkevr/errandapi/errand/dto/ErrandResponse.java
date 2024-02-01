package dev.mrkevr.errandapi.errand.dto;

import java.time.LocalDate;

import dev.mrkevr.errandapi.errand.api.ErrandCategory;
import dev.mrkevr.errandapi.errand.api.ErrandLocation;
import dev.mrkevr.errandapi.errand.api.ErrandStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrandResponse {

	String id;

	String employerUsername;
	
	String agentUsername;

	String title;

	String description;

	String imageUrl;

	ErrandCategory errandCategory;

	ErrandLocation errandLocation;
	
	String contact;

	double compensation;

	ErrandStatus errandStatus;

	LocalDate posted;
	
	long timeElapsedInHours;
}
