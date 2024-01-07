package dev.mrkevr.errandapi.imagefile.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.common.service.ServerService;
import dev.mrkevr.errandapi.imagefile.exception.ImageFileNotFoundException;

@Component
@Transactional(readOnly = true)
public class ImageFileService {
	
	@Autowired
	private ImageFileRepository imageFileRepository;
	
	@Value("${file.image.directory}")
	private String imageDirectory;
	
	@Autowired
	private ServerService serverService;
	
	private final String BASE_URL = "http://localhost:9001/api/images/";
		
	/*
	 * Accept multipartFile and return its url
	 */
	@Transactional
	public String save(MultipartFile file) throws IOException {
		
		String fileName = this.generateRandomString(8);
		String filePath = imageDirectory + File.separator + fileName + this.getFileExtension(file.getOriginalFilename());
		
		try {
			// Save the image file to directory
			Files.copy(
				file.getInputStream(),
				Paths.get(filePath),
				StandardCopyOption.REPLACE_EXISTING);
			// Save the image file's data to db
			ImageFile imageFile = ImageFile.builder()
				.fileName(fileName)
				.filePath(filePath)
				.build();
			imageFileRepository.save(imageFile);
			return serverService.getBaseUri().concat("/images/").concat(fileName);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public byte[] getImageByFileName(String fileName) {
		
		ImageFile imageFile = imageFileRepository.findByFileName(fileName)
				.orElseThrow(() -> new ImageFileNotFoundException("Could not found file with name: " + fileName));
		
		File file = new File(imageFile.getFilePath());
		if(!file.isFile() || !file.canRead()) {
			throw new RuntimeException("Image file not found");
		}
		
		try {
            Path imagePath = Paths.get(imageFile.getFilePath());
            // Load the image file as a resource
            Resource resource = new UrlResource(imagePath.toUri());
            // Read the image file into an InputStream
            try (InputStream inputStream = resource.getInputStream()) {
                // Read the image bytes into a byte array
                byte[] imageBytes = inputStream.readAllBytes();
                // Return the image bytes in the response
                return imageBytes;
            }
        } catch (IOException e) {
            // Handle exceptions such as file not found or IO errors
            e.printStackTrace();
            throw new RuntimeException("Image file reading error");
        }
	}
	
	// Remove all characters before the last 'DOT' from the name.
	private String getFileExtension(String name) {
		String extension;
		try {
			extension = name.substring(name.lastIndexOf("."));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
	
	// Generate random String for image file
	private String generateRandomString(int length) {
        // Define the characters allowed in the random string
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Create a StringBuilder to store the generated string
        StringBuilder randomStringBuilder = new StringBuilder(length);
        // Create an instance of Random
        Random random = new Random();
        // Generate random characters and append them to the StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomStringBuilder.append(randomChar);
        }
        // Convert StringBuilder to String and return
        return randomStringBuilder.toString();
    }
}
