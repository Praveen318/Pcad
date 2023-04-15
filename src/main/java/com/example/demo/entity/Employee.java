package com.example.demo.entity;

//JPA entity class that can be used to store information about employees in a database.

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private short id;
	private String name;
	@Column(unique = true)
	private String mobile;
	@Column(unique = true)
	private String email;
	private String status;
	@Column(unique = true)
	private String entryexitcode;
	private String password;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime logintime;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime logouttime;
	private long totalhrs;
	private String roles;
}
