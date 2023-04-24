package com.employee.dto;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class OP {

	private int id;
	
	private Date startTime;
	private Date endTime;
	private Date createdTime;
	
	private Date bookingDate;
	private List<String> reports;
	private int patientId ;
	
}
