package com.example.reservePlace.dto;

import com.example.reservePlace.model.ReserveEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveDTO {
	private String key;
	private String title;
	private boolean done;
	
	public ReserveDTO(final ReserveEntity entity) {
		this.key = entity.getKey();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static ReserveEntity toEntity(final ReserveDTO dto) {
		return ReserveEntity.builder()
				.Key(dto.getKey())
				.title(dto.getTitle())
				.done(dto.isDone()).build();
	}
}
