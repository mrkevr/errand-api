package dev.mrkevr.errandapi.errand.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import dev.mrkevr.errandapi.common.util.NullOrEmptyChecker;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrandSpecifications {

	public static Specification<Errand> hasErrandCategory(List<ErrandCategory> errandCategories) {
		return (root, query, builder) -> root.get("errandCategory").in(errandCategories);
	}

	public static Specification<Errand> hasErrandStatus(List<ErrandStatus> errandStatuses) {
		return (root, query, builder) -> root.get("errandStatus").in(errandStatuses);
	}

	public static Specification<Errand> hasKeyword(String keyword) {
		String lowerKeyword = keyword.toLowerCase();
		return (root, query, builder) -> {
			Predicate titlePredicate = builder.like(root.get("title"), "%" + lowerKeyword + "%");
			Predicate descriptionPredicate = builder.like(root.get("description"), "%" + lowerKeyword + "%");
			return builder.or(titlePredicate, descriptionPredicate);
		};
	}
	
	public static Specification<Errand> postedLastNDays(Integer days) {
		LocalDateTime startDate = LocalDateTime.now().minusDays(days);
		return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("created"), startDate);
	}
	
	public static Specification<Errand> hasCompensationGreaterThanOrEqualTo(double minCompensation) {
        return (root, query, builder) ->
            builder.greaterThanOrEqualTo(root.get("compensation"), minCompensation);
    }

    public static Specification<Errand> hasCompensationLessThanOrEqualTo(double maxCompensation) {
        return (root, query, builder) ->
            builder.lessThanOrEqualTo(root.get("compensation"), maxCompensation);
    }

    public static Specification<Errand> hasCompensationBetween(double minCompensation, double maxCompensation) {
        return (root, query, builder) ->
            builder.between(root.get("compensation"), minCompensation, maxCompensation);
    }
    
    public static <T> Specification<Errand> addSpecificationIfNotNullOrEmpty(
			Specification<Errand> specifications, 
			T value, 
			Function<T, Specification<Errand>> specificationFunction) {
		
	    if (!NullOrEmptyChecker.isNullOrEmpty(value)) {
	    	Specification<Errand> spec = specificationFunction.apply(value);
	    	if (Objects.nonNull(spec)) {
	            specifications = specifications.and(spec);
	        }
	    }
	    return specifications;
	}
}
