package com.practicecoding.crmproject.employeecrm.dao;

import java.util.List;

import com.practicecoding.crmproject.employeecrm.entities.EmployeeDetails;
import com.practicecoding.crmproject.employeecrm.entities.EmployeeInfo;

public interface EmployeeInfoDao {

	public List<EmployeeInfo> findAll();
	
	public EmployeeInfo findById(int id);
	
	public EmployeeInfo findByUsername(String username);
	
	public void save(EmployeeInfo employeeInfo);
		
	public void deleteById(int id);
	
	public boolean doesEmployeeExist(String firstName, String middleName, String lastName);
}
