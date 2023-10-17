package com.example.reservePlace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reservePlace.dto.ResponseDTO;
import com.example.reservePlace.dto.UserDTO;
import com.example.reservePlace.model.UserEntity;
import com.example.reservePlace.security.TokenProvider;
import com.example.reservePlace.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping("/signup")
	public ResponseEntity<?>registerUser(@RequestBody UserDTO userDTO){
		try {
			UserEntity user = UserEntity.builder()
				.uid(userDTO.getUid())
				.username(userDTO.getUsername())
				.phone(userDTO.getPhone())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.build();
			
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = userDTO.builder()
				.uid(registeredUser.getUid())
				.num(registeredUser.getNum())
				.phone(registeredUser.getPhone())
				.username(registeredUser.getUsername())
				.build();
			
			return ResponseEntity.ok().body(responseUserDTO);
		}catch(Exception e){
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
		UserEntity user = userService.getByCredentials(userDTO.getUid(),
				userDTO.getPassword(), passwordEncoder);
		if(user !=null){
			final String token = tokenProvider.create(user);
			final UserDTO responseUserDTO = UserDTO.builder()
				.uid(user.getUid())
				.num(user.getNum())
				.token(token)
				.build();
			return ResponseEntity.ok().body(responseUserDTO);
			} else {
				ResponseDTO responseDTO = ResponseDTO.builder()
				.error("Login failed").build();
		return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
