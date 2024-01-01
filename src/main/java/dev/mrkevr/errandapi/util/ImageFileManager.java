package dev.mrkevr.errandapi.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageFileManager {
	
	@Value("${file.image.directory}")
	private String imageDirectory;

	public String uploadImageFileToDirectory(String fileName, MultipartFile file) throws IOException {
		String filePath = imageDirectory + File.separator + fileName + this.getFileExtension(file.getOriginalFilename());
		try {
			Files.copy(
				file.getInputStream(),
				Paths.get(filePath),
				StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return filePath;
	}

	public byte[] getImageByFilePath(String filePath) {
		
		File file = new File(filePath);
		if(!file.isFile() || !file.canRead()) {
			throw new RuntimeException("Image file not found");
		}
		
		try {
            Path imagePath = Paths.get(filePath);
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
	
	/**
	 * Remove all characters before the last 'DOT' from the name.
	 */
	private String getFileExtension(String name) {
		String extension;
		try {
			extension = name.substring(name.lastIndexOf("."));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
}
