package dev.mrkevr.errandapi.errand.api;

import static dev.mrkevr.errandapi.errand.api.ErrandSpecifications.addSpecificationIfNotNullOrEmpty;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.common.exception.ApiException;
import dev.mrkevr.errandapi.common.util.NullOrEmptyChecker;
import dev.mrkevr.errandapi.errand.dto.ErrandCreationRequest;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import dev.mrkevr.errandapi.errand.exception.ErrandNotFoundException;
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
	
	public List<ErrandResponse> getAll() {
		List<Errand> errands = errandRepository.findAll();
		return errandMapper.map(errands);
	}

	public ErrandResponse getById(String id) {
		Errand errand = errandRepository.findById(id).orElseThrow(ErrandNotFoundException::new);
		return errandMapper.map(errand);
	}
	
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
	
	public List<ErrandResponse> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Errand> errands = errandRepository.findAll(pageRequest);
		return errandMapper.map(errands.getContent());
	}

	public List<ErrandResponse> searchBySpecifications(
			String keyword, 
			List<ErrandCategory> errandCategories, 
			Integer days, 
			List<ErrandStatus> errandStatuses,
			Double minCompensation,
			Double maxCompensation) {
		
		// At least one must not be empty/null, else return exception
		if (NullOrEmptyChecker.allNullOrEmpty(keyword, errandCategories, days, errandStatuses, minCompensation, maxCompensation)) {
			throw new ApiException("Search values are all empty/null");
		}
		
		// Initialize specifications
		Specification<Errand> specifications  = Specification.where(null);
		// Add category filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, errandCategories, ErrandSpecifications::hasErrandCategory);
	    // Add keyword filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, keyword, ErrandSpecifications::hasKeyword);
	    // Add days last posted filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, days, ErrandSpecifications::postedLastNDays);
	    // Add status filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, errandStatuses, ErrandSpecifications::hasErrandStatus);
	    // Add compensation range filters
		specifications = addSpecificationIfNotNullOrEmpty(specifications, minCompensation, ErrandSpecifications::hasCompensationGreaterThanOrEqualTo);
		specifications = addSpecificationIfNotNullOrEmpty(specifications, maxCompensation, ErrandSpecifications::hasCompensationLessThanOrEqualTo);
		
	    // Find all the errands using all the specifications
	    List<Errand> errands = errandRepository.findAll(specifications);
	    // Map to response object and return
		return errandMapper.map(errands);
	}

	public List<ErrandResponse> searchBySpecifications(
			String keyword, 
			List<ErrandCategory> errandCategories,
			Integer days) {
		
		// At least one must not be empty/null, else return exception
		if (NullOrEmptyChecker.allNullOrEmpty(keyword, errandCategories, days)) {
			throw new ApiException("Search values are all empty/null");
		}
		
		// Initialize specifications
		Specification<Errand> specifications  = Specification.where(null);
		// Add category filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, errandCategories, ErrandSpecifications::hasErrandCategory);
	    // Add keyword filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, keyword, ErrandSpecifications::hasKeyword);
	    // Add days last posted filter
		specifications = addSpecificationIfNotNullOrEmpty(specifications, days, ErrandSpecifications::postedLastNDays);
		
		// Find all the errands using all the specifications
	    List<Errand> errands = errandRepository.findAll(specifications);
	    // Map to response object and return
		return errandMapper.map(errands);
	}

	public List<ErrandResponse> findLatestErrands(int limit) {
		List<Errand> errands = errandRepository.findLastCreatedErrands(Limit.of(limit));
		return errandMapper.map(errands);
	}
}
