package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Employee;
import com.example.demo.repository.Employeerepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private Employeerepository repository;
	//overriding a method of interface UserDetailsService to verify username
	@Override
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		Optional<Employee> userInfo=repository.findByMobile(mobile);
		return userInfo.map(UserInfoUserDetails::new)
			.orElseThrow(()->new UsernameNotFoundException(
					"No employee with mobilenumber: " +mobile+" found"));
	}

}
