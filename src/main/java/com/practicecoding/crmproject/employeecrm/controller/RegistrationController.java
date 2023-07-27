package com.practicecoding.crmproject.employeecrm.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.context.properties.PropertyMapper.Source;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicecoding.crmproject.employeecrm.entities.EmployeeInfo;
import com.practicecoding.crmproject.employeecrm.model.Employee;
import com.practicecoding.crmproject.employeecrm.service.EmployeeInfoService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserDetailsManager userDetailsManager;
	
	@Autowired
	private EmployeeInfoService employeeService;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("employeeInfo", new EmployeeInfo());
		theModel.addAttribute("disabled", null);
		theModel.addAttribute("heading", "Register new Employee");
		theModel.addAttribute("processName", "processRegistrationForm");
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@ModelAttribute("employeeInfo") EmployeeInfo employeeInfo,
			/*BindingResult bindingResult,*/
			Model model) {
		String userName = createUserName(employeeInfo.getFirstName(), employeeInfo.getLastName());
		
		employeeInfo.setUsername(userName);
		
		logger.info("Processing registration form for: "+userName);
		
		boolean employeeExist = employeeService.doesEmployeeExist(
				employeeInfo.getFirstName(), employeeInfo.getMiddleName(),
				employeeInfo.getLastName());
		
		if(employeeExist) {
			if(employeeInfo.getId()!=0) {
				employeeService.save(employeeInfo);
				model.addAttribute("processSuccessTitle", "Update Confirmation");
				model.addAttribute("processSuccessMessage", "Employee Updated Successfully");
				return "registration-confirmation";
			}else {
				model.addAttribute("employeeInfo", new EmployeeInfo());
				model.addAttribute("registrationError", "Employee already exists.");
	//			model.addAttribute("disabled", "");
				model.addAttribute("heading", "Register new Employee");
				
				logger.warning("Employee already exists.");
				
				return "registration-form";
			}
		}
		
		//encrypt the password
		String encodedPassword = passwordEncoder.encode("welcome@123");
		
		// prepend the endoding algorithm id
		encodedPassword = "{bcrypt}" + encodedPassword;
		
		// give the user default role of "employee"
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
	
		// create user object (from Spring Security framework)
		User tempUser = new User(userName,encodedPassword,authorities);
		
		//save user in the database
		userDetailsManager.createUser(tempUser);
		
		employeeService.save(employeeInfo);
		
		logger.info("Successfully created user: " + userName);
		model.addAttribute("processSuccessTitle", "Registration Confirmation");
		model.addAttribute("processSuccessMessage", "Employee Registered Successfully");
		
		return "registration-confirmation";
	}
	
	@GetMapping("/updateEmployee")
	public String updateEmployee(@RequestParam("employeeId") int id,
									Model model) {
		
		EmployeeInfo employeeInfo = employeeService.findById(id);
		
		model.addAttribute("disabled", null);
		model.addAttribute("employeeInfo", employeeInfo);
		model.addAttribute("heading", "Update Existing Employee");
		model.addAttribute("processName", "updateRegistrationForm");
		
		return "registration-form";
	}
	
	@PostMapping("/updateRegistrationForm")
	public String updateRegistrationForm(@ModelAttribute("employeeInfo")EmployeeInfo employee,
					Model model) {
		employeeService.save(employee);
		model.addAttribute("processSuccessTitle", "Update Confirmation");
		model.addAttribute("processSuccessMessage", "Employee Updated Successfully");
		
		return "registration-confirmation";
	}
	
	private boolean doesUserExist(String userName) {
		
		logger.info("Checking if user exist: "+userName);
		
		//check the database if user already exists
		boolean exists = userDetailsManager.userExists(userName);
		
		logger.info("User: " + userName + ", exists: " +exists);
		
		return exists;
	}
	
	
	private String createUserName(String firstName, String lastName) {
		String userName = lastName + firstName.charAt(0);
		int saltNum = 1;
		boolean userExists = doesUserExist(userName);
		do {
			userExists = doesUserExist(userName);
			if(!userExists) {
				return userName;
			}
			userName = lastName + firstName.charAt(0);
			logger.info("Adding numbers for unique name");
			
			userName = userName + "0" + saltNum;
			saltNum++;
		}while(userExists);
		
		return userName;
	}
	
}




