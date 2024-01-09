package dev.mrkevr.errandapi.errand.api;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.errand.dto.ErrandCreationRequest;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import dev.mrkevr.errandapi.errand.util.ErrandMapper;
import dev.mrkevr.errandapi.imagefile.api.ImageFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrandService {
	
	ErrandMapper errandMapper;
	ImageFileService imageFileService;
	ErrandRepository errandRepository;
	
	public ErrandResponse add(
			ErrandCreationRequest errandCreationRequest,
			MultipartFile errandImageFile) throws IOException {
		
		Errand errandToSave = errandMapper.map(errandCreationRequest);
		
		// Save the image file and save the image url to user
		String imageUrl = imageFileService.save(errandImageFile);
		errandToSave.setImageUrl(imageUrl);
		
		Errand savedErrand = errandRepository.saveAndFlush(errandToSave);
		return errandMapper.map(savedErrand);
	}
	
	
	
	
}
