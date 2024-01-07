package dev.mrkevr.errandapi.user.api;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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
import dev.mrkevr.errandapi.common.service.ServerService;
import dev.mrkevr.errandapi.common.validator.ValidImageFile;
import dev.mrkevr.errandapi.user.dto.UserCreationRequest;
import dev.mrkevr.errandapi.user.dto.UserResponse;
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
	ServerService serverService;
	
	@GetMapping
	ResponseEntity<ResponseEntityBody> getAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "1000") int size,
			@RequestParam(required = false) String query) {
		
		List<UserResponse> userResponses = null;
		if (ObjectUtils.isEmpty(query) || query == null) {
			userResponses = userService.getAll(page, size);
		} else {
			userResponses = userService.getAllWithQuery(page, size, query);
		}
		
		String title = "Users";
		Map<String, Object> body = new HashMap<>();
		body.put("page", page);
		body.put("size", size);
		body.put("users", userResponses);
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, body);
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<ResponseEntityBody> getById(@PathVariable String id) {
		UserResponse userResponse = userService.getById(id);

		String title = "User found successfully";
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.OK, userResponse);
		return ResponseEntity.ok(responseEntityBody);
	}
	
	@PostMapping
	ResponseEntity<ResponseEntityBody> save(
			@Valid @RequestPart(name = "creationRequest") UserCreationRequest userCreationRequest,
			@Valid @ValidImageFile @RequestParam(name = "avatarImageFile", required = true) MultipartFile avatarImageFile) throws IOException {
		
		UserResponse userResponse = userService.add(userCreationRequest, avatarImageFile);
		String title = "User created successfully";
		String uri = serverService.getBaseUri().concat("/users/") + userResponse.getId();
		
		ResponseEntityBody responseEntityBody = ResponseEntityBody.of(title, HttpStatus.CREATED, userResponse);
		return ResponseEntity.created(URI.create(uri)).body(responseEntityBody);
	}
}
