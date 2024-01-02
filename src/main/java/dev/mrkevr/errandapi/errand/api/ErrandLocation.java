package dev.mrkevr.errandapi.errand.api;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrandLocation {

	@NotBlank(message = "Field `address` is must not be blank")
	String address;

	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$", message = "Field `latitude` is invalid")
	double latitude;

	@Pattern(regexp = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$", message = "Field `longitude` is invalid")
	double longitude;
}