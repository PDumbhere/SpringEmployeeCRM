package com.practicecoding.crmproject.employeecrm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicecoding.crmproject.employeecrm.entities.EmployeeInfo;
import com.practicecoding.crmproject.employeecrm.service.EmployeeInfoService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeInfoService employeeService;
	
	@GetMapping("/")
	public String showHomePage() {
		return "home";
	}
	
	@GetMapping("/seePersonalInfo")
	public String seePersonalInfo(@AuthenticationPrincipal User user, Model model) {
		
		String username = user.getUsername();
		
		EmployeeInfo employeeInfo = employeeService.findByUsername(username);
		
		model.addAttribute("disabled", "disabled");
		model.addAttribute("employeeInfo", employeeInfo);
		model.addAttribute("heading", "Employee Personal Info");
		
		return "registration-form";
	}
	@GetMapping("/viewEmployees")
	public String viewEmployeeList(Model model) {
		
		List<EmployeeInfo> employees = employeeService.findAll();
		
		model.addAttribute("employees", employees);
		
		return "list-employees";
	}
	@GetMapping("/seeEmployeeDetails")
	public String seeEmployeeDetails(@RequestParam("employeeId") int id,
										Model model) {
		EmployeeInfo employee = employeeService.findById(id);
		
		model.addAttribute("editable", "true");
		model.addAttribute("disabled", "disabled");
		model.addAttribute("employeeInfo", employee);
		model.addAttribute("heading", "Employee Personal Info");
		
		return "registration-form";
	}
}
