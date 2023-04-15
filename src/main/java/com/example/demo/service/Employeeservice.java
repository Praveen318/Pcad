package com.example.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DataReq;
import com.example.demo.dto.EmployeeData;
import com.example.demo.entity.Employee;
import com.example.demo.globalExpceptionHandler.CustomException;
import com.example.demo.repository.Employeerepository;


@Service
public class Employeeservice {

	@Autowired
	private Employeerepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	//employee signup details added by manager
	public String saveEmployee(DataReq datareq)
	{	
		Employee employee=Convertor.reqtoemp(datareq);
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		try {
		repository.save(employee);
		return "Employee Added "+employee.getId()+"-"+employee.getName();
		}catch(DataIntegrityViolationException ex)
		{return "Duplicate entry check mobile number, email, password";}	
	}

	
	//list of employees signup details added by manager
	public String saveEmployees(List<DataReq> datareq)
	{
		Iterator<DataReq> iterator = datareq.iterator();
		List<Employee> employee=new ArrayList<Employee>();
		String str="";
		while(iterator.hasNext())
		{	Employee emp=Convertor.reqtoemp(iterator.next());
			emp.setPassword(passwordEncoder.encode(emp.getPassword()));
			employee.add(emp);
		}
		try {
		repository.saveAll(employee);
		Iterator<Employee> iterator1 = employee.iterator();
		Employee emp;
		while(iterator1.hasNext())
		{
			emp = iterator1.next();
			str+=" "+emp.getId()+"-"+emp.getName()+"\n";
		}
		return "Employees Added: \n"+ str+"\b";
		}catch(DataIntegrityViolationException ex)
		{return "Duplicate entry check mobile number, email, password";}
	}

	
	//list of employees can be accessed by manager
	public List<EmployeeData> getEmployees()
	{
		return Convertor.emptodata(repository.findAll());
	}
		
	
	
	//manager can find employees detail by name
	public List<EmployeeData> getEmployeeByName(String name) throws CustomException
	{
			List<EmployeeData> emp=Convertor.emptodata(
					repository.findByName(name));
			if(emp.isEmpty()==false)
			{return emp;}
			else
			{throw new CustomException("No Employee with name: "+name);}
	}

	
	//manager can find employee details by mobile number
	public EmployeeData getEmployeeByMobile(String mobile) throws CustomException{
		Employee emp=repository.findByMobile(mobile).orElseThrow(()-> new CustomException(
				"No Employee with mobile number: "+mobile));
			return Convertor.emptodata(emp);	
	}
	
		
	//manager can delete employee details by using idno.
	public String deleteEmployeeById(short id)
	{
		repository.deleteById(id);
		return "Employee removed!!"+id;
	}		
	
	
	//any employee can see their id
	public EmployeeData getprofile(String usermobile)
	{
			return Convertor.emptodata(repository.findByMobile(usermobile).orElse(null));
		}

	
	//any employee can update their own mobile number
	public String getupdateMobile(String usermobile,String mobile)
	{
		try {
		Employee existingEmployee = repository.findByMobile(usermobile).orElse(null);
		existingEmployee.setMobile(mobile);
		repository.save(existingEmployee);
		return existingEmployee.getName()+" updated mobile number "+existingEmployee.getMobile()
		+"-Logged_out-Please login with new number";
		}catch(Exception ex)
		{return "This mobile number is already present";}
		}
	
	
	//any employee can update their own email id
	public String getupdateEmail(String usermobile,String email)
	{
		try {
		Employee existingEmployee = repository.findByMobile(usermobile).orElse(null);
		existingEmployee.setEmail(email);
		repository.save(existingEmployee);
		return existingEmployee.getName()+" updated email "+existingEmployee.getEmail();
		}catch(Exception ex)
		{return "This email id is already present";}
		}


	//any employee can update their unique entryexitcode
	public String getupdateEntryExitCode(String usermobile,String entryexitcode)
	{
		try{Employee existingEmployee = repository.findByMobile(usermobile).orElse(null);
		existingEmployee.setEntryexitcode(entryexitcode);
		repository.save(existingEmployee);
		return existingEmployee.getName()+"'s EntryExitCode updated";}
		catch(Exception ex)
		{return "This EntryExitCode is already present";}
	}
	
	
	//any employee register there entry and exit time using unique EntryExitCode
	public String getupdateStatus(String entryexitcode)
	{
		
		List<Employee> employee=repository.findAll();
		Iterator<Employee> iterator=employee.iterator();
		String str="EntryExitCode_Mismatch";
		while(iterator.hasNext())
		{	Employee emp=iterator.next();
			String str1=emp.getEntryexitcode();
			if(str1.equals(entryexitcode))
			{
				if(emp.getStatus().equals("Logged_out")){
					emp.setStatus("Logged_in");
					LocalDateTime logintime=LocalDateTime.now();
					emp.setLogintime(logintime);
					emp.setLogouttime(null);
					repository.save(emp);
					str=emp.getId()+" "+emp.getStatus();
				}
				else if(emp.getStatus().equals("Logged_in")) {
					LocalDateTime logouttime=LocalDateTime.now();
					LocalDateTime logintime=emp.getLogintime();
					long t=Duration.between(logintime,logouttime).toSeconds();
					if(t<=90)
					{
					emp.setTotalhrs(emp.getTotalhrs()+t);
					emp.setLogouttime(logouttime);
					emp.setStatus("Logged_out");
					repository.save(emp);
					str=emp.getId()+" "+emp.getStatus();
					}
					else
					{
						str="Contact Manager";
					}
				}
				break;
			}		
		}
		return str;		
	}
	
	
	//manager can manually exit any employee by entering date time and id
	public String getupdatelogout(short id, String time)throws CustomException {	
		Employee existingEmployee = repository.findById(id).orElse(null);
		if(existingEmployee!=null)
		{
			if(existingEmployee.getStatus().equals("Logged_in"))
				{
				try {
					existingEmployee.setLogouttime(LocalDateTime.parse(time));
					long t=Duration.between(existingEmployee.getLogintime(),
							existingEmployee.getLogouttime()).toSeconds();
					long t1=Duration.between(existingEmployee.getLogouttime(),
							LocalDateTime.now()).toSeconds();
						if(t>=0&&t1>=0)
						{
							t+=existingEmployee.getTotalhrs();
							existingEmployee.setTotalhrs(t);
							existingEmployee.setStatus("Logged_out");
							repository.save(existingEmployee);
							return id+" loggedout at "+existingEmployee.getLogouttime();
						}
						else
						{
							return "Wrong time";
						}
					}catch(Exception ex) {
						throw new CustomException("Wrong datetimeformat");
					}
				}
			else return id+" not logged in";
		}
		else return id+" not found";
	}
	
	
	//manager can reset total working hrs, entryexit time  	
	public String getreset(short id) {
	try {
		Employee existingEmployee = repository.findById(id).orElse(null);
		existingEmployee.setLogintime(null);
		existingEmployee.setLogouttime(null);
		existingEmployee.setTotalhrs(0);
		existingEmployee.setStatus("Logged_out");
		repository.save(existingEmployee);
		return id+" Reset";
	}catch(Exception ex)
	{
	return id+" not found";	
	}
}
	
}
