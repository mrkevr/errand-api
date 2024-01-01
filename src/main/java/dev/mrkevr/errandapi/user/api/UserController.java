package dev.mrkevr.errandapi.user.api;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import dev.mrkevr.errandapi.common.dto.ResponseEntityBody;
import dev.mrkevr.errandapi.common.validator.ValidImageFile;
import dev.mrkevr.errandapi.user.dto.UserCreationRequest;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import dev.mrkevr.errandapi.util.ImageFileManager;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserController {
	
	UserService userService;
	ImageFileManager imageFileManager;
	
	@GetMapping
	ResponseEntity<ResponseEntityBody> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1000") int size) {
		List<UserResponse> userResponses = userService.getAll(page, size);
		
		String title = "Users found";
		Map<String, Object> body = new HashMap<>();
		body.put("page", page);
		body.put("size", size);
		body.put("users", userResponses);
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, body);
		return ResponseEntity.ok(responseEntityBody);
	}

	@GetMapping("/{id}")
	ResponseEntity<ResponseEntityBody> getByID(@PathVariable String id) {
		UserResponse userResponse = userService.getById(id);

		String title = "User found successfully";
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, Map.of("user", userResponse));
		return ResponseEntity.ok(responseEntityBody);
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
	ResponseEntity<ResponseEntityBody> saveUser(
			@Valid @RequestPart(name = "creationRequest") UserCreationRequest creationRequest,
			@Valid @ValidImageFile @RequestParam(name = "avatarImageFile", required = true) MultipartFile avatarImageFile) {
		
		UserResponse userResponse = userService.addUser(creationRequest, avatarImageFile);
		String title = "User is successfully created";
		String uri = "/users/" + userResponse.getId();
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.CREATED, Map.of("savedUser", userResponse));
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
}
