package com.practicecoding.crmproject.employeecrm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practicecoding.crmproject.employeecrm.entities.EmployeeDetails;
import com.practicecoding.crmproject.employeecrm.entities.EmployeeInfo;

@Repository
public class EmployeeInfoDaoImpl implements EmployeeInfoDao {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<EmployeeInfo> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<EmployeeInfo> query = 
				currentSession.createQuery("from EmployeeInfo", EmployeeInfo.class);
		
		List<EmployeeInfo> employeesInfo = query.getResultList();
		
		return employeesInfo;
	}

	@Override
	public EmployeeInfo findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		EmployeeInfo employeeInfo = 
				currentSession.get(EmployeeInfo.class, id);
		
		return employeeInfo;
	}

	@Override
	public void save(EmployeeInfo employeeInfo) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		if(employeeInfo.getId()==0) {
			currentSession.save(employeeInfo);			
		}else {
			currentSession.update(employeeInfo);
		}
		

	}
	
//	public void update(EmployeeInfo employeeInfo) {
//		
//		Session currentSession = entityManager.unwrap(Session.class);
//		
//		currentSession
//		
//		currentSession.update(employeeInfo);
//	}
	
	

	@Override
	public void deleteById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<EmployeeInfo> query = 
				currentSession.createQuery(
						"delete from EmployeeInfo where id=:employeeId");
		query.setParameter("employeeId", id);
		
		query.executeUpdate();

	}

	@Override
	public boolean doesEmployeeExist(String firstName, String middleName, String lastName) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query query = currentSession.createQuery(
				"from EmployeeInfo e where e.firstName=:firstName"
				+ " and e.middleName=:middleName and e.lastName=:lastName");
		query.setParameter("firstName", firstName);
		query.setParameter("middleName", middleName);
		query.setParameter("lastName", lastName);
		
		List<EmployeeInfo> employeesInfo = query.getResultList();
		
		if(employeesInfo.isEmpty()) {
			return false;
		}
		
		return true;
		
	}

	@Override
	public EmployeeInfo findByUsername(String username) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query query = currentSession.createQuery(
				"from EmployeeInfo e where e.username=:username");
		query.setParameter("username", username);
		
		List<EmployeeInfo> result = query.getResultList();
		
		return result.get(0);
	}
	
	

}
