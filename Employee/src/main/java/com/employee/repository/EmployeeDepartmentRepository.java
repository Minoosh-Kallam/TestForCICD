package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.EmployeeDepartment;

import jakarta.transaction.Transactional;

public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer>{

	public List<EmployeeDepartment> findAllByEmployeeId(int id);
	
	@Query("delete from EmployeeDepartment details where details.employeeId = ?1 and departmentId = ?2")
	@Modifying
	@Transactional
	public void removeEmployeesDepartment(int employeeId, int departmentId);
}
