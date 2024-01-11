package dev.mrkevr.errandapi.errand.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ErrandRepository extends JpaRepository<Errand, String>, JpaSpecificationExecutor<Errand> {

	List<Errand> findByEmployerUsername(String employerUsername, Pageable pageable);

	@Query("SELECT e FROM Errand e WHERE e.employerUsername = :employerUsername AND e.errandStatus = :errandStatus")
	List<Errand> findByEmployerIdAndErrandStatus(String employerUsername, ErrandStatus errandStatus, Pageable pageable);
}
