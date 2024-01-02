package dev.mrkevr.errandapi.errand.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.mrkevr.errandapi.errand.api.Errand;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;

@Component
public class ErrandMapper {
	
	private final String BASE_URL = "http://localhost:9001/api/errands/";
	
	public ErrandResponse map(Errand errand) {
		return ErrandResponse.builder()
			.id(errand.getId())
			.userId(errand.getUserId())
			.title(errand.getTitle())
			.description(errand.getDescription())
			.image(BASE_URL + errand.getId() + "/image")
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
