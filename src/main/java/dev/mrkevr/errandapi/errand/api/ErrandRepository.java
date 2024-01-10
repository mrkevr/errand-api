package dev.mrkevr.errandapi.errand.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ErrandRepository extends JpaRepository<Errand, String>, JpaSpecificationExecutor<Errand> {

	List<Errand> findByEmployerId(String employerId, Pageable pageable);

	@Query("SELECT e FROM Errand e WHERE e.employerId = :employerId AND e.errandStatus = :errandStatus")
	List<Errand> findByEmployerIdAndErrandStatus(String employerId, ErrandStatus errandStatus, Pageable pageable);
}
