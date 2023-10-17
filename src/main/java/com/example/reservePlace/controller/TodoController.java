package com.example.reservePlace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservePlace.dto.ResponseDTO;
import com.example.reservePlace.dto.TodoDTO;
import com.example.reservePlace.model.TodoEntity;
import com.example.reservePlace.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("todo")
public class TodoController {
	@Autowired
	private TodoService service;
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userNum, @RequestBody TodoDTO dto) {
		try {
			// dto �� �̿��� ���̺� �����ϱ� ���� entity�� �����Ѵ�.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// entity userId�� �ӽ÷� �����Ѵ�
			entity.setNum(null);
			entity.setUserNum(userNum);
			// service.create �� ���� repository �� entity�� �����Ѵ�.
			// �̶� �Ѿ��� ���� ���� ���� �����Ƿ� List�� �ƴ� Optional �� �Ѵ�.
			List<TodoEntity> entities = service.create(entity);
			
			// entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			log.info("Log:entities => dtos ok!");
		
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			log.info("Log:responsedto ok!");
			//HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@GetMapping
	public ResponseEntity<?> retrieveTodo(@AuthenticationPrincipal String userNum) {
		List<TodoEntity> entities = service.retrieve(userNum);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		//HTTP Status 200 ���·� response �� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userNum,@RequestBody TodoDTO dto) {
		try {
			// dto �� �̿��� ���̺� �����ϱ� ���� entity�� �����Ѵ�.
			TodoEntity entity = TodoDTO.toEntity(dto);
			// entity userId�� �ӽ÷� �����Ѵ�.
			entity.setUserNum(userNum);
			// service.create �� ���� repository �� entity�� �����Ѵ�.
			// �̶� �Ѿ��� ���� ���� ���� �����Ƿ� List�� �ƴ� Optional �� �Ѵ�.
			List<TodoEntity> entities = service.update(entity);
			// entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			// Response DTO�� �����Ѵ�.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			// HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userNum,@RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			// entity userId�� �ӽ÷� �����Ѵ�.
			entity.setUserNum(userNum);
			List<TodoEntity> entities = service.delete(entity);
			//entities �� dtos �� ��Ʈ�� ��ȯ�Ѵ�.
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			//Response DTO�� �����Ѵ�.
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			//HTTP Status 200 ���·� response �� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}