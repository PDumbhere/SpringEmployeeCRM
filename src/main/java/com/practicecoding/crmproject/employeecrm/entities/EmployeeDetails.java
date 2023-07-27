package com.practicecoding.crmproject.employeecrm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_details")
public class EmployeeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name="age")
	private int age;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "marital_status")
	private String maritalStatus;
	
	@Column(name = "address")
	private String address;

	public EmployeeDetails() {
		
	}

	public EmployeeDetails(String phone, int age, String sex, String maritalStatus, String address) {
		super();
		this.phone = phone;
		this.age = age;
		this.sex = sex;
		this.maritalStatus = maritalStatus;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "EmployeeDetails [id=" + id + ", phone=" + phone + ", age=" + age + ", sex=" + sex + ", maritalStatus="
				+ maritalStatus + ", address=" + address + "]";
	}
	
	
	
}
