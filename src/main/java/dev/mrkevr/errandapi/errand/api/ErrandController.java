package dev.mrkevr.errandapi.errand.api;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
			@ValidImageFile(width = 300, height = 300, message = "Please upload a valid image file(png/jpg, 50kb or less, 300x300px)") 
			@RequestParam(name = "errandImageFile", required = true) 
			MultipartFile errandImageFile) throws IOException {
		
		ErrandResponse errandResponse = errandService.add(errandCreationRequest, errandImageFile);
		String title = "Errand posted successfully";
		String uri = serverService.getBaseUri().concat("/users/") + errandResponse.getId();
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.CREATED, errandResponse);
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
}
