package dev.mrkevr.errandapi.library.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class GenericEntity implements Serializable {

	@Id
	@GenericGenerator(name = "entity_id_seq", type = GeneticEntityIdGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_seq")
	@Column(name = "id", updatable = false)
	private String id;

	@CreationTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime modified;

	public abstract String getIdPrefix();
}
