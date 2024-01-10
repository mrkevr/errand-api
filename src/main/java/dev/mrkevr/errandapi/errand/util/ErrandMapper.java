package dev.mrkevr.errandapi.errand.util;

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
			.employerId(errandCreationRequest.getEmployerId())
			.title(errandCreationRequest.getTitle())
			.description(errandCreationRequest.getDescription())
			.errandLocation(errandCreationRequest.getErrandLocation())
			.compensation(errandCreationRequest.getCompensation())
			.errandCategory(errandCreationRequest.getErrandCategory())
			.errandStatus(ErrandStatus.VACANT)
			.build();
		
		System.out.println(errand);
		
		return errand;
	}
	
	public ErrandResponse map(Errand errand) {
		return ErrandResponse.builder()
			.id(errand.getId())
			.employerId(errand.getEmployerId())
			.agentId(errand.getAgentId())
			.title(errand.getTitle())
			.description(errand.getDescription())
			.imageUrl(errand.getImageUrl())
			.errandLocation(errand.getErrandLocation())
			.errandCategory(errand.getErrandCategory())
			.compensation(errand.getCompensation())
			.errandStatus(errand.getErrandStatus())
			.posted(errand.getCreated().toLocalDate())
			.build();
	}
	
	public List<ErrandResponse> map(List<Errand> errands) {
		return errands.stream()
			.map(errand -> map(errand))
			.collect(Collectors.toList());
	}
}
