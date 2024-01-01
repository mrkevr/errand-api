package dev.mrkevr.errandapi.user.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.library.validator.ValidImageFile;
import dev.mrkevr.errandapi.user.dto.UserCreationRequest;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import dev.mrkevr.errandapi.util.ImageFileManager;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserController {
	
	UserService userService;
	ImageFileManager imageFileManager;
	
	@GetMapping
	ResponseEntity<List<UserResponse>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1000") int size) {
		List<UserResponse> userResponses = userService.getAll(page, size);
		return ResponseEntity.ok(userResponses);
	}

	@GetMapping("/{id}")
	ResponseEntity<UserResponse> getByID(@PathVariable String id) {
		UserResponse userResponse = userService.getById(id);
		return ResponseEntity.ok(userResponse);
	}
	
	@GetMapping("/{id}/avatar")
	ResponseEntity<byte[]> getAvatarById(@PathVariable String id) {
		
		String avatar = userService.getAvatarById(id);
		
		byte[] imageBytes = imageFileManager.getImageByFilePath(avatar);
		
		// Set appropriate headers for the image response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG); // Change the media type based on your image format
		headers.setContentLength(imageBytes.length);
			
		// Return the image bytes in the response
		return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	}
	
	@PostMapping
	ResponseEntity<?> saveUser(
			@Valid @RequestPart UserCreationRequest creationRequest,
			@Valid @ValidImageFile @RequestParam(name = "avatarImageFile", required = true) MultipartFile avatarImageFile) {
		
		UserResponse userResponse = userService.addUser(creationRequest, avatarImageFile);
		String uri = "/users/" + userResponse.getUsername();
		String message = "User is successfully created";
		
//		ResponseEntityBody body = ResponseEntityBody.builder()
//			.title(HttpStatus.CREATED.toString())
//			.status(HttpStatus.CREATED.value())
//			.timeStamp(LocalDateTime.now())
//			.message(message)
//			.build();
		
		return ResponseEntity.created(URI.create(uri)).body(userResponse);
	}
}
