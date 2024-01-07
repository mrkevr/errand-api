package dev.mrkevr.errandapi.imagefile.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageFileController {
	
	ImageFileService imageFileService;
	
	@GetMapping("/{fileName}")
	ResponseEntity<byte[]> getAvatarById(@PathVariable String fileName) {
		byte[] imageBytes = imageFileService.getImageByFileName(fileName);
		
		// Set appropriate headers for the image response
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG); // Change the media type based on your image format
		headers.setContentLength(imageBytes.length);
			
		// Return the image bytes in the response
		return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	}
}
