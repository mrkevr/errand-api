package dev.mrkevr.errandapi.errand.dto;

import dev.mrkevr.errandapi.errand.api.ErrandCategory;
import dev.mrkevr.errandapi.errand.api.ErrandLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrandCreationRequest {

	@NotEmpty(message = "Field `employerId` must not be empty")
	@Size(min = 10, max = 12, message = "Field `employerId` must be 10-12 chatacters")
	String employerId;

	@NotEmpty(message = "Field `title` must not be empty")
	@Size(min = 6, max = 120, message = "Field `title` must be 6-120 chatacters")
	String title;

	@NotEmpty(message = "Field `description` must not be empty")
	@Size(min = 6, max = 240, message = "Field `description` must be 6-240 chatacters")
	String description;

	@NotNull(message = "Field `errandCategory` must not be null")
	ErrandCategory errandCategory;

	@Valid
	@NotNull(message = "Field `errandLocation` must not be null")
	ErrandLocation errandLocation;

	@NotNull(message = "Field `compensation` must not be null")
	@Positive(message = "Field `compensation` must be greater than 0")
	@DecimalMax(value = "1000000.00", message = "Field `compensation` must not be greater than P1,000,000.00")
	double compensation;
}
