package com.example.reservePlace.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservePlace.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
	UserEntity findByUserId(String id);
	Boolean existsByUserId(String id);
	UserEntity findByUserIdAndPassword(String id, String password);
}