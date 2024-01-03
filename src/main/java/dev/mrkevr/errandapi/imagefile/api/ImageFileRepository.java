package dev.mrkevr.errandapi.imagefile.api;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, String> {

	Optional<ImageFile> findByFileName(String fileName);

}
