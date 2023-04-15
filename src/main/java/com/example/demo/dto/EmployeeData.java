package com.example.demo.dto;


//dto for information of employee to fetch each details of employees or profile is called

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Setter;
//import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeData {

		private short id;
		private String name;
		private String roles;
		private String mobile;
		private String email;
		private String status;
		private String entryexitcode;
		private LocalDateTime logintime;
		private LocalDateTime logouttime;
		private long totalhrs;
		
		
}