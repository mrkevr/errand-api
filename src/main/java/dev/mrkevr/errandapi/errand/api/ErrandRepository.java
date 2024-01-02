package dev.mrkevr.errandapi.errand.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ErrandRepository extends JpaRepository<Errand, String> {

	List<Errand> findByUserId(String userId, Pageable pageable);

	@Query("SELECT e FROM Errand e WHERE e.userId = :userId AND e.errandStatus = :errandStatus")
	List<Errand> findByErrandStatus(String userId, ErrandStatus errandStatus, Pageable pageable);
}
