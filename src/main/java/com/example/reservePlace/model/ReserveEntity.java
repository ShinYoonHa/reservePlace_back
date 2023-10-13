package com.example.reservePlace.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Reserve")
public class ReserveEntity {

	@Id
	@GeneratedValue(generator="system-uukey") //자동으로  id 생성
	@GenericGenerator(name="system-uukey",strategy="uukey")
	private String Key;
	private String userKey;
	private String title;
	private boolean done;
}
