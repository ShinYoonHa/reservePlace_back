package com.example.reservePlace.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservePlace.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
	UserEntity findByUid(String uid);
	Boolean existsByUid(String uid);
	UserEntity findByUidAndPassword(String uid, String password);
}