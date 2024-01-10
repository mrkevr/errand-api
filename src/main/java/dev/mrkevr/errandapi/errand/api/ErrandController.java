package dev.mrkevr.errandapi.errand.api;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
			@ValidImageFile(width = 500, height = 500, message = "Please upload a valid image file(png/jpg, 50kb or less, 500x500px)") 
			@RequestParam(name = "errandImageFile", required = true) 
			MultipartFile errandImageFile) throws IOException {
		
		System.out.println(errandCreationRequest);
		
		ErrandResponse errandResponse = errandService.add(errandCreationRequest, errandImageFile);
		String title = "Errand posted successfully";
		String uri = serverService.getBaseUri().concat("/errands/") + errandResponse.getId();
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.CREATED, errandResponse);
		
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
	
	@GetMapping
	ResponseEntity<ResponseEntityBody> getAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "1000") int size) {
		
		List<ErrandResponse> errands = errandService.getAll(page, size);
		
		String title = "Errands";
		Map<String, Object> body = new HashMap<>();
		body.put("page", page);
		body.put("size", size);
		body.put("errands", errands);
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, body);
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@GetMapping("/search")
	ResponseEntity<ResponseEntityBody> search(
			@RequestParam(required = false) String keyword, 
			@RequestParam(required = false) List<ErrandCategory> errandCategories,
			@RequestParam(required = false) Integer days,
			@RequestParam(required = false) List<ErrandStatus> errandStatuses) {
		
		List<ErrandResponse> errands = errandService.searchBySpecifications(keyword, errandCategories, days, errandStatuses);
		
		String title = "Errands Search";
		Map<String, Object> body = new HashMap<>();
		body.put("keyword", StringUtils.hasText(keyword) ? keyword : "null");
		body.put("errandCategories", ObjectUtils.isEmpty(errandCategories) ? "null" : errandCategories);
		body.put("errandStatus", ObjectUtils.isEmpty(errandStatuses) ? "null" : errandStatuses);
		body.put("last posted in days", Objects.isNull(days) ? "null" : days);
		body.put("found", errands.size());
		body.put("errands", errands);
		
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, body);
		return ResponseEntity.ok(responseEntityBody);
	}
}
