package dev.mrkevr.errandapi.user.api;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
