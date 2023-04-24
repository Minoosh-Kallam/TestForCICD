package com.employee.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private String firstName;
	private String lastName;
	private String address;
	private String mobileNo;
	private String altMobileNo;
	@Column(unique = true)
	private String email;
	
	private int primeDeptId;
	


	private String password;
	private String token;
	private boolean isTokenExpired;

	
	private float experience;
	private Date dateOfJoin;
	private String designisation;
	private String gender;
	
	
}
