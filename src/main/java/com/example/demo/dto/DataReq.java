package com.example.demo.dto;

//dto for employee information to be added for signing up

import com.example.demo.annotations.MobileNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DataReq {
		@NotBlank(message="Enter a vaild name")
		@Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only alphabets")
		private String name;
		@MobileNumber
		private String mobile;		
		@Email(message="Enter a valid email")
		private String email;
		@Pattern(message="Password must atleast contain a capital letter[A-Z],"
				+ " a small letter(a-z), a special character[!@#&()–[{}]:;',?/*~$^+=<>]"
				+ " and a number[0-9] and must be 6 character long",
				regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
						+ "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,}$")
		private String password;
		@NotBlank(message="Enter a vaild code")
		private String entryexitcode;
		private String status="Logged_out"; 
		@NotBlank(message="Enter Valid Role")
		private String roles;
}
