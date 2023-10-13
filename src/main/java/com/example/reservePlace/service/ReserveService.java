package com.example.reservePlace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservePlace.model.ReserveEntity;
import com.example.reservePlace.persistence.ReserveRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReserveService {
	@Autowired
	private ReserveRepository repository;
	
	public List<ReserveEntity> create(final ReserveEntity entity) {
		//Validations
		validate(entity);
		repository.save(entity);
		//return repository.findById(entity.getId());
		return repository.findByUserKey(entity.getUserKey());
	}
	public List<ReserveEntity> retrieve(final String userKey) {
		return repository.findByUserKey(userKey);
	}
	public List<ReserveEntity> update(final ReserveEntity entity) {
		//Validations
		validate(entity);
		if (repository.existsById(entity.getKey())) {
			repository.save(entity);
		} else throw new RuntimeException("Unknown Key");
		//return repository.findById(entity.getId());
		return repository.findByUserKey(entity.getUserKey());
	}
	public List<ReserveEntity> delete(final ReserveEntity entity) {
		if(repository.existsById(entity.getKey()))
		repository.deleteById(entity.getKey());
		else throw new RuntimeException("id does not exist");
		return repository.findByUserKey(entity.getUserKey());
	}
	public void validate(final ReserveEntity entity) {
		if(entity == null ) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		if(entity.getUserKey() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}