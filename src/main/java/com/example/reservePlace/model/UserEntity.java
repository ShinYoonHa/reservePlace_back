package com.example.reservePlace.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class UserEntity {
	@Id
	@GeneratedValue(generator="system-uukey")
	@GenericGenerator(name="system-uukey",strategy="uukey")
	private String key;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String phone;
	
	@Column(nullable=false)
	private String id;
	
	@Column(nullable=false)
	private String password;
}
