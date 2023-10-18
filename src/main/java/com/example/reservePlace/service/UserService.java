package com.example.reservePlace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reservePlace.dto.UserDTO;
import com.example.reservePlace.model.UserEntity;
import com.example.reservePlace.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;	
	
	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getUid() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String uid = userEntity.getUid();
		if(userRepository.existsByUid(uid)) {
			log.warn("Id already exists {}",uid);
			throw new RuntimeException("Id already exists");
		}
		return userRepository.save(userEntity);
	}
	public UserEntity getByCredentials(final String uid, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUid(uid);
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
	public UserEntity getUserEntity(final String uid){
        return userRepository.findByUid(uid);
    }

    public void updateUserEntity(final UserEntity userEntity){
        userRepository.save(userEntity);
    }

}