package com.example.demo.controller;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.DataReq;
import com.example.demo.dto.EmployeeData;
import com.example.demo.globalExpceptionHandler.CustomException;
import com.example.demo.service.Employeeservice;
import com.example.demo.service.JwtService;
import com.example.demo.Validator.ValidList;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class Controller 
{

	
	@Autowired
	private Employeeservice service;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//HomePage
	@GetMapping("/pcad/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	
	//Authenticating User login details
	@PostMapping("/pcad/Login")
	public String authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest)
			throws CustomException {
		Authentication authentication=authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
						authRequest.getUsermobile(), authRequest.getPassword()));
			if(authentication.isAuthenticated())
				return jwtService.generateToken(authRequest.getUsermobile());
			else
				throw new CustomException("No authorities given to Employee");
	}
	
	
	
	//employee signup details added by manager
	@PostMapping("/pcad/addEmployee")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String addEmployee(@Valid @RequestBody DataReq datareq){
		return service.saveEmployee(datareq);
	}
	
	
	
	//list of employees signup details added by manager
	@PostMapping("/pcad/addEmployees")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String addEmployees(@Valid @RequestBody ValidList<DataReq> datareq){
		List<DataReq> data=datareq.getEmployee();
		return service.saveEmployees(data);
	}
	
	
	
	//list of employees can be accessed by manager
	@GetMapping("/pcad/employees")
	@PreAuthorize("hasAuthority('MANAGER')")
	public List<EmployeeData> findAllEmployees()
	{
		return service.getEmployees();
	}
	
	
	
	//manager can find employees detail by name
	@GetMapping("/pcad/employeeByName")
	@PreAuthorize("hasAuthority('MANAGER')")
	public List<EmployeeData> findEmployeeByName(@RequestBody String name) throws
	CustomException
	{
		
		if(((Pattern.compile("^[a-zA-Z]+$")).matcher(name)).matches())
		{
		return service.getEmployeeByName(name);}
		else
		{
			throw new CustomException("Enter Valid name");}
	}
	
	
	
	//manager can find employee details by mobile number
	@GetMapping("/pcad/employeeByMobile/{mobile}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public EmployeeData findEmployeeByMobile(@PathVariable String mobile) throws CustomException
	{
		if(mobile.matches("\\d{10}"))
		{
		return service.getEmployeeByMobile(mobile);}
		else
		{
			throw new CustomException("Enter Valid mobile number");}
	}
	
	
	
	//manager can delete employee details by using idno.
	@DeleteMapping("/pcad/delete/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String deleteEmployee(@PathVariable String id)throws CustomException{
		if(id.matches("\\d{1,6}")&&Integer.parseInt(id)<32768)
		{short id1=Short.parseShort(id);
			return service.deleteEmployeeById(id1);
		}
		else
			throw new CustomException("Enter Valid id");
		}
	
	
	
	//any employee can see their id
	@GetMapping("/pcad/Profile")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public EmployeeData profile(HttpServletRequest request) {
		String authHeader=request.getHeader("Authorization");
		String token=authHeader.substring(7);
		String usermobile=jwtService.extractUsermobile(token);
		return service.getprofile(usermobile);
	}
	
	
	
	//any employee can update their own mobile number
	@PutMapping("/pcad/updateMobile")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String updateMobile(HttpServletRequest request,@RequestBody String mobile) throws CustomException
	{
		if(mobile.matches("\\d{10}"))
		{
			String authHeader=request.getHeader("Authorization");
			String token=authHeader.substring(7);
			String usermobile=jwtService.extractUsermobile(token);
		return service.getupdateMobile(usermobile,mobile);}
		else {
			throw new CustomException("Enter Valid mobile number");
		}
	}
	
	
	
	//any employee can update their own email id
	@PutMapping("/pcad/updateEmail")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String updateEmail(HttpServletRequest request,@RequestBody String email)throws CustomException
	{
		if(email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
		{
			String authHeader=request.getHeader("Authorization");
			String token=authHeader.substring(7);
			String usermobile=jwtService.extractUsermobile(token);
		return service.getupdateEmail(usermobile,email);}
		else {
			throw new CustomException("Enter Valid email id");
		}
	}
	
	
	
	//any employee can update their unique entryexitcode
	@PutMapping("/pcad/updateEntryExitCode")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String updateEntryExitCode(HttpServletRequest request,
			@PathVariable String id,@RequestBody String entryexitcode){
		String authHeader=request.getHeader("Authorization");
		String token=authHeader.substring(7);
		String usermobile=jwtService.extractUsermobile(token);
		return service.getupdateEntryExitCode(usermobile,entryexitcode);		
	}
	
	
	
	//any employee register there entry and exit time using unique EntryExitCode
	@PutMapping("/pcad/EntryExit")
	//@PreAuthorize("hasAnyAuthority('MANAGER','EMPLOYEE')")
	public String upadteStatus(@RequestBody String entryexitcode) {
		return service.getupdateStatus(entryexitcode); 
	}
	
	
	//manager can manually exit any employee by entering date time and id
	@PutMapping("/pcad/Exit/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String updatelogout(@PathVariable String id,@RequestBody String time)
			throws CustomException{
		if(id.matches("\\d{1,6}")&&Integer.parseInt(id)<32768)
		{short id1=Short.parseShort(id);
		return service.getupdatelogout(id1,time); 
		}
		else
			throw new CustomException("Enter Valid id");		
		}
	
	
	//manager can reset total working hrs, entryexit time  
	@PostMapping("/pcad/Reset/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String reset(@PathVariable String id)throws CustomException
	{
		if(id.matches("\\d{1,6}")&&Integer.parseInt(id)<32768)
		{short id1=Short.parseShort(id);
		return service.getreset(id1);
		}
		else
			throw new CustomException("Enter Valid id");
	}

}

