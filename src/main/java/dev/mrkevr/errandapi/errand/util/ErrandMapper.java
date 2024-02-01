package dev.mrkevr.errandapi.errand.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.errand.api.Errand;
import dev.mrkevr.errandapi.errand.api.ErrandStatus;
import dev.mrkevr.errandapi.errand.dto.ErrandCreationRequest;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;

@Component
public class ErrandMapper {
	
	/*
	 * Create a new Errand from ErrandCreationRequest's values
	 */
	public Errand map(ErrandCreationRequest errandCreationRequest) {
		
		Errand errand = Errand.builder()
			.employerUsername(errandCreationRequest.getEmployerUsername())
			.title(errandCreationRequest.getTitle())
			.description(errandCreationRequest.getDescription())
			.errandLocation(errandCreationRequest.getErrandLocation())
			.compensation(errandCreationRequest.getCompensation())
			.errandCategory(errandCreationRequest.getErrandCategory())
			.errandStatus(ErrandStatus.VACANT)
			.build();
		return errand;
	}
	
	public ErrandResponse map(Errand errand) {
		return ErrandResponse.builder()
			.id(errand.getId())
			.employerUsername(errand.getEmployerUsername())
			.agentUsername(errand.getAgentUsername())
			.title(errand.getTitle())
			.description(errand.getDescription())
			.imageUrl(errand.getImageUrl())
			.errandLocation(errand.getErrandLocation())
			.contact(errand.getContact())
			.errandCategory(errand.getErrandCategory())
			.compensation(errand.getCompensation())
			.errandStatus(errand.getErrandStatus())
			.posted(errand.getCreated().toLocalDate())
			.timeElapsedInHours(this.getTimeElapsedInHours(errand.getCreated()))
			.build();
	}
	
	public List<ErrandResponse> map(List<Errand> errands) {
		return errands.stream()
			.map(errand -> map(errand))
			.collect(Collectors.toList());
	}
	
	private long getTimeElapsedInHours(LocalDateTime postedDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(postedDateTime, currentDateTime);
        return duration.toHours();
    }
}
