package com.example.reservePlace.dto;

import com.example.reservePlace.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	private String num;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.num = entity.getNum();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.num(dto.getNum())
				.title(dto.getTitle())
				.done(dto.isDone()).build();
	}
}
