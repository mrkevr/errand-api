package dev.mrkevr.errandapi.errand.api;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.common.dto.ResponseEntityBody;
import dev.mrkevr.errandapi.common.service.ServerService;
import dev.mrkevr.errandapi.common.validator.ValidImageFile;
import dev.mrkevr.errandapi.errand.dto.ErrandCreationRequest;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Validated
@RestController
@RequestMapping("/errands")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrandController {
	
	ErrandService errandService;
	ServerService serverService;
	
	@PostMapping
	ResponseEntity<ResponseEntityBody> save(
			@Valid 
			@RequestPart(name = "errandCreationRequest") 
			ErrandCreationRequest errandCreationRequest,
			@Valid 
			@ValidImageFile(
					minWidth = 300, 
					maxWidth = 500, 
					minHeight = 300,
					maxHeight = 500,
					message = "Please upload a valid image file(png/jpg, 100kb or less, 300-500x300-500px)") 
			@RequestParam(name = "errandImageFile", required = true) 
			MultipartFile errandImageFile) throws IOException {
		
		
		ErrandResponse errandResponse = errandService.add(errandCreationRequest, errandImageFile);
		String uri = serverService.getBaseUri().concat("/errands/") + errandResponse.getId();
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of("Errand created successfully", HttpStatus.CREATED, errandResponse);
		
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
	
	@GetMapping
	ResponseEntity<ResponseEntityBody> getAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "1000") int size) {
		
		List<ErrandResponse> errands = errandService.getAll(page, size);
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of("Errands", HttpStatus.OK, errands);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(errands.size()));
		
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@GetMapping("/search")
	ResponseEntity<ResponseEntityBody> search(
			@RequestParam(required = false) String keyword, 
			@RequestParam(required = false) List<ErrandCategory> errandCategories,
			@RequestParam(required = false) Integer days,
			@RequestParam(required = false) List<ErrandStatus> errandStatuses, 
			@RequestParam(required = false) Double minCompensation,
			@RequestParam(required = false) Double maxCompensation) {
		
		List<ErrandResponse> errands = errandService.searchBySpecifications(
				keyword, errandCategories, days,
				errandStatuses, minCompensation, maxCompensation);
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of("Errands Search", HttpStatus.OK, errands);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(errands.size()));
		
		return new ResponseEntity<ResponseEntityBody>(responseEntityBody, headers, HttpStatus.OK);
	}
}
