package dev.mrkevr.errandapi.errand.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.common.exception.ApiException;
import dev.mrkevr.errandapi.errand.dto.ErrandCreationRequest;
import dev.mrkevr.errandapi.errand.dto.ErrandResponse;
import dev.mrkevr.errandapi.errand.util.ErrandMapper;
import dev.mrkevr.errandapi.imagefile.api.ImageFileService;
import jakarta.persistence.criteria.Predicate;
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
	
	public List<ErrandResponse> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Errand> errands = errandRepository.findAll(pageRequest);
		return errandMapper.map(errands.getContent());
	}

	public List<ErrandResponse> searchBySpecifications(
			String keyword, 
			List<ErrandCategory> errandCategories, 
			Integer days, 
			List<ErrandStatus> errandStatuses) {
		
		// Atleast one must not be empty/null, else return exception
		if (!StringUtils.hasText(keyword) && Objects.isNull(errandCategories) && Objects.isNull(days) && Objects.isNull(errandStatuses)) {
			throw new ApiException("Search queries are all empty/null");
		}
		
		// Initialize specifications
		Specification<Errand> specifications  = Specification.where(null);
		// Add category filter
		if (errandCategories != null && !errandCategories.isEmpty()) {
			specifications = specifications.and(ErrandSpecifications.hasErrandCategory(errandCategories));
		}
		// Add keyword filter
		if (StringUtils.hasText(keyword)) {
			specifications = specifications.and(ErrandSpecifications.hasKeyword(keyword));
		}
		// Add days last posted filter
		if (!Objects.isNull(days)) {
			specifications = specifications.and(ErrandSpecifications.postedLastNDays(days));
		}
		// Add status filter
		if (errandStatuses != null && !errandStatuses.isEmpty()) {
			specifications = specifications.and(ErrandSpecifications.hasErrandStatus(errandStatuses));
		}
		
		List<Errand> errands = errandRepository.findAll(specifications);
		
		return errandMapper.map(errands);
	}
}
