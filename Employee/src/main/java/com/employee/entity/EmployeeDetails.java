package com.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Employee_Additional_Details")
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeDetails {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id ;
	
	private int employeeId;
	private int departmentId;

}
