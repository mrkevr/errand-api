package dev.mrkevr.errandapi.testimonial.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestimonialRepository extends JpaRepository<Testimonial, String> {

	@Query("SELECT t FROM Testimonial t WHERE t.userId = :userId")
	Page<Testimonial> findByUserId(@Param("userId") String userId, Pageable pageable);
}
