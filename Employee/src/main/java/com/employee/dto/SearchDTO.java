package com.employee.dto;

import java.util.List;

import com.employee.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class SearchDTO {

	List<Employee> employees;
	List<Department> departments;
//	List<Update> updates;
//	List<Post> posts;
}
