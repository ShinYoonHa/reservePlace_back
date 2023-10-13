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
import com.example.reservePlace.dto.ReserveDTO;
import com.example.reservePlace.model.ReserveEntity;
import com.example.reservePlace.service.ReserveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("todo")
public class ReserveController {
	@Autowired
	private ReserveService service;
	
	@PostMapping
	public ResponseEntity<?> createReserve(@AuthenticationPrincipal String userKey,	@RequestBody ReserveDTO dto) {
		try {
			// dto 를 이용해 테이블에 저장하기 위한 entity를 생성한다.
			ReserveEntity entity = ReserveDTO.toEntity(dto);
			// entity userId를 임시로 지정한다
			entity.setKey(null);
			entity.setUserKey(userKey);
			// service.create 를 통해 repository 에 entity를 저장한다.
			// 이때 넘어노는 값이 없을 수도 있으므로 List가 아닌 Optional 로 한다.
			List<ReserveEntity> entities = service.create(entity);
			
			// entities 를 dtos 로 스트림 변환한다.
			List<ReserveDTO> dtos = entities.stream().map(ReserveDTO::new).collect(Collectors.toList());
			log.info("Log:entities => dtos ok!");
		
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().data(dtos).build();
			log.info("Log:responsedto ok!");
			//HTTP Status 200 상태로 response 를 전송한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@GetMapping
	public ResponseEntity<?> retrieveReserve(@AuthenticationPrincipal String userKey) {
		List<ReserveEntity> entities = service.retrieve(userKey);
		List<ReserveDTO> dtos = entities.stream().map(ReserveDTO::new).collect(Collectors.toList());
		ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().data(dtos).build();
		//HTTP Status 200 상태로 response 를 전송한다.
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateReserve(@AuthenticationPrincipal String userKey,@RequestBody ReserveDTO dto) {
		try {
			// dto 를 이용해 테이블에 저장하기 위한 entity를 생성한다.
			ReserveEntity entity = ReserveDTO.toEntity(dto);
			// entity userId를 임시로 지정한다.
			entity.setUserKey(userKey);
			// service.create 를 통해 repository 에 entity를 저장한다.
			// 이때 넘어노는 값이 없을 수도 있으므로 List가 아닌 Optional 로 한다.
			List<ReserveEntity> entities = service.update(entity);
			// entities 를 dtos 로 스트림 변환한다.
			List<ReserveDTO> dtos = entities.stream().map(ReserveDTO::new).collect(Collectors.toList());
			// Response DTO를 생성한다.
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().data(dtos).build();
			// HTTP Status 200 상태로 response 를 전송한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userKey,@RequestBody ReserveDTO dto) {
		try {
			ReserveEntity entity = ReserveDTO.toEntity(dto);
			// entity userId를 임시로 지정한다.
			entity.setUserKey(userKey);
			List<ReserveEntity> entities = service.delete(entity);
			//entities 를 dtos 로 스트림 변환한다.
			List<ReserveDTO> dtos = entities.stream().map(ReserveDTO::new).collect(Collectors.toList());
			//Response DTO를 생성한다.
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().data(dtos).build();
			//HTTP Status 200 상태로 response 를 전송한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<ReserveDTO> response = ResponseDTO.<ReserveDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}