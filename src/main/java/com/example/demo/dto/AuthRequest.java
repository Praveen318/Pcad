package com.example.demo.dto;


//dto for Login information
import com.example.demo.annotations.MobileNumber;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	@MobileNumber
	String usermobile;
	@Pattern(message="Password must atleast contain a capital letter[A-Z],"
			+ " a small letter(a-z), a special character[!@#&()–[{}]:;',?/*~$^+=<>]"
			+ " and a number[0-9] and must be 6 character long",
			regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
					+ "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,}$")
	String password;
}
