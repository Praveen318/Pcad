package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import com.example.demo.dto.DataReq;
import com.example.demo.dto.EmployeeData;
import com.example.demo.entity.Employee;


public class Convertor {

	//creating static object of modelMapper
	private static ModelMapper modelMapper = new ModelMapper();
	
	//extracting data of fields of Employee to Employeedata class
	public static EmployeeData emptodata(Employee emp)
	{
		EmployeeData employeedata=modelMapper.map(emp, EmployeeData.class);
		return employeedata;
	}
	//extracting data of fields of Datareq class to Employee class
	public static Employee reqtoemp(DataReq req)
	{
		Employee employee=modelMapper.map(req, Employee.class);
		return employee;
	}
	//extracting data of fields of List of Employee class to Employeedata
	public static List<EmployeeData>  emptodata(List<Employee> emp)
	{
		List<EmployeeData> map = emp.stream()
								.map(Employee -> modelMapper.map(Employee, EmployeeData.class))
								.collect(Collectors.toList());
		return map;
	}

}
