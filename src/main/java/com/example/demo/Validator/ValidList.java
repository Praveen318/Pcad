package com.example.demo.Validator;

//purpose of this generic class is to validate a list of datareq objects, 
//ensuring that each object in the list satisfies the validation constraints
//defined for the datareq class.


import java.util.List;

import jakarta.validation.Valid;

public class ValidList<datareq> {


	private List<@Valid datareq> employee;
	
	public ValidList() {
	}
	public List<datareq> getEmployee() {
		return employee;
	}
	public void setEmployee(List<datareq> employee) {
		this.employee = employee;
	}
}
