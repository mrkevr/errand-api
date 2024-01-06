package dev.mrkevr.errandapi.user.api;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

//	@Query("SELECT u FROM User u WHERE LOWER(u.title) LIKE LOWER(:query) OR LOWER(u.name) LIKE LOWER(:query)")
	@Query("SELECT u FROM User u WHERE LOWER(u.title) LIKE %?1% OR LOWER(u.name) LIKE %?1%")
	Page<User> findByQuery(String query, Pageable pageable);

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
	
	@Query("UPDATE User u SET u.errandsWorked = u.errandsWorked + 1 WHERE u.id = :id")
	void incrementErrandsWorkedById(String id);
	
	@Query("UPDATE User u "
			+ "SET u.rateScore = u.rateScore + :increaseBy, "
			+ "u.timesRated = u.timesRated + 1 "
			+ "WHERE u.id = :id")
	void rateUserById(String id, int increaseBy);
}
