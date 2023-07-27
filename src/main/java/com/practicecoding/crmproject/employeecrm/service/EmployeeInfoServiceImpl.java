package com.practicecoding.crmproject.employeecrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practicecoding.crmproject.employeecrm.dao.EmployeeInfoDao;
import com.practicecoding.crmproject.employeecrm.entities.EmployeeInfo;

@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

	@Autowired
	private EmployeeInfoDao employeeInfoDao;
	
	@Override
	@Transactional
	public List<EmployeeInfo> findAll() {
		return employeeInfoDao.findAll();
	}

	@Override
	@Transactional
	public EmployeeInfo findById(int id) {
		return employeeInfoDao.findById(id);
	}

	@Override
	@Transactional
	public void save(EmployeeInfo employeeInfo) {
		employeeInfoDao.save(employeeInfo);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		employeeInfoDao.deleteById(id);
	}

	@Override
	@Transactional
	public boolean doesEmployeeExist(String firstName, String middleName, String lastName) {
		return employeeInfoDao.doesEmployeeExist(firstName, middleName, lastName);
	}

	@Override
	@Transactional
	public EmployeeInfo findByUsername(String username) {
		return employeeInfoDao.findByUsername(username);
	}
	
	

}
