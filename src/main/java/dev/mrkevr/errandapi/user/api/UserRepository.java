package dev.mrkevr.errandapi.user.api;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
	@Query("SELECT u.avatar FROM User u WHERE u.id = :id")
	Optional<String> getAvatarById(String id);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
