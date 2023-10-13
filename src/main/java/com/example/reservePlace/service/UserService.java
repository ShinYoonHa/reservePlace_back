package com.example.reservePlace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reservePlace.model.UserEntity;
import com.example.reservePlace.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getId() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String id = userEntity.getId();
		if(userRepository.existsByUserId(id)) {
			log.warn("Email already exists {}",id);
			throw new RuntimeException("Id already exists");
		}
		return userRepository.save(userEntity);
	}
	public UserEntity getByCredentials(final String id, final String password,
			final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUserId(id);
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
}