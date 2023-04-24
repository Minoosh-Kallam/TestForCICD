package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.EmployeeDetails;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Integer>{

	public List<EmployeeDetails> findAllByEmployeeId(int id);
	
	@Query("delete from EmployeeDetails details where details.employeeId = ?1 and departmentId = ?2")
	@Modifying
	public void removeEmployeesDepartment(int employeeId, int departmentId);
}
