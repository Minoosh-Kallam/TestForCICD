package com.employee.service;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import com.employee.entity.Employee;
import com.employee.entity.EmployeeDetails;
import com.employee.repository.EmployeeDetailsRepository;
import com.employee.repository.EmployeeRepo;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
	
	@Mock
	private EmployeeRepo employeeRepo;
	
	@Mock
	private EmployeeDetailsRepository employeeDetailsrepo ;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks 
	private EmployeeService employeeService;
	
	Employee employee ;
	
	
	@BeforeEach
	public void setUp() {
		employee = Employee.builder()
				.id(1)
				.firstName("minoosh")
				.lastName("reddy")
				.address("Hyderabad")
				.build();
	}
	
	
	@Test
	public void givenEmployee_whenSaveEmployee_thenReturnSavedEmployee() {
		
		
		given(employeeRepo.save(employee)).willReturn(employee);
		
		Employee savedEmployee = employeeService.addEmployee(employee);		
		
		assertThat(savedEmployee).isNotNull();
		
	}
	
	

}
