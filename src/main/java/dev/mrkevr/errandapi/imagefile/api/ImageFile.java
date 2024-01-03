package dev.mrkevr.errandapi.imagefile.api;

import dev.mrkevr.errandapi.common.entity.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "image_files")
@Entity(name = "ImageGile")
public class ImageFile extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "file_name")
	String fileName;

	@Column(name = "file_path")
	String filePath;

	@Override
	public String getIdPrefix() {
		return "IMFL";
	}
}
