package com.example.reservePlace.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.reservePlace.model.ReserveEntity;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity,String>{
	@Query("select t from TodoEntity t where t.userKey = ?1")
	List<ReserveEntity>findByUserKey(String userKey);
}