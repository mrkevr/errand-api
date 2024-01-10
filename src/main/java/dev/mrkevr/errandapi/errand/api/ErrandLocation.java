package dev.mrkevr.errandapi.errand.api;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@Column(name = "address")
	String address;

//	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$", message = "Field `latitude` is invalid")
//	@Pattern(regexp = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$", message = "Field `latitude` is invalid")
	
	@NotNull(message = "Field `latitude` must not be null")
	@DecimalMin(value = "-90.0", inclusive = true, message = "Field `latitude` must be between -90 and 90")
    @DecimalMax(value = "90.0", inclusive = true, message = "Field `latitude` must be between -90 and 90")
    @Digits(integer = 2, fraction = 6, message = "Field `latitude` must have at most 2 integer and 6 fractional digits")
	@Column(name = "latitude")
	Double latitude;

//	@Pattern(regexp = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$", message = "Field `longitude` is invalid")
//	@Pattern(regexp = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$", message = "Field `longitude` is invalid")
	
	@DecimalMin(value = "-180.0", inclusive = true, message = "Field `longitude` must be between -180 and 180")
    @DecimalMax(value = "180.0", inclusive = true, message = "Field `longitude` must be between -180 and 180")
    @Digits(integer = 3, fraction = 6, message = "Field `longitude` must have at most 3 integer and 6 fractional digits")
	@NotNull(message = "Field `longitude` must not be null")
	@Column(name = "longitude")
	Double longitude;
}