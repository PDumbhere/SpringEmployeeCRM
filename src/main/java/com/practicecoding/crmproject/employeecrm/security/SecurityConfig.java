package com.practicecoding.crmproject.employeecrm.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	// add support for JDBC ... 
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
	
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(configurer ->
							configurer
									.antMatchers("/").hasRole("EMPLOYEE")
									.antMatchers("/leaders/**").hasRole("MANAGER")
									.antMatchers("/systems/**").hasRole("ADMIN")
									.anyRequest().authenticated()
						)
						.formLogin(form ->
								form
									.loginPage("/showMyLoginPage")
									.loginProcessingUrl("/authenticateTheUser")
									.permitAll()
						)
						.logout(logout -> logout.permitAll()
						);
			return http.build();
						
	}
}
