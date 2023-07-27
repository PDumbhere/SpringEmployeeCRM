package com.practicecoding.crmproject.employeecrm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "enabled")
	private int enabled;
	
	public User() {
		
	}

	public String getUsername() {
		return username;
	}

	public int getEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", enabled=" + enabled + "]";
	}
	
	
}
