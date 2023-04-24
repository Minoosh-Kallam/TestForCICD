package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dto.Department;
import com.employee.dto.OP;
import com.employee.dto.PatientOpDTO;
import com.employee.dto.SearchDTO;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService ;
	
	@GetMapping("")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees() ;
	}
	
	@GetMapping("{id}")
	public Employee getEmployee(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}
	
	@PostMapping(value = "" , consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		employee = employeeService.addEmployee(employee ) ;
		
		return new ResponseEntity<Employee>(employee , HttpStatus.CREATED);
	}
	
	@PutMapping(value = "" , consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
		employee = employeeService.updateEmployee(employee ) ;
		
		return new ResponseEntity<Employee>(employee , HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>("Employee id "+id+" is successfully deleted" , HttpStatus.NO_CONTENT );
	}
	
	@GetMapping("{id}/departments")
	public List<Department> getDepartmentsOfEmployee(@PathVariable int id){
		return employeeService.getDepartmentsOfEmployee(id);
	}
	
	@PostMapping("{employeeId}/departments/{departmentId}")
	public ResponseEntity<String> addDepartmentToEmployee(@PathVariable int employeeId,@PathVariable int departmentId ) {
		employeeService.addDeptToEmployee(employeeId, departmentId);
		return new ResponseEntity<>("the department is assigned to employee", HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("{employeeId}/departments/{departmentId}")
	public void removeDepartmentFromEmployee(@PathVariable int employeeId,@PathVariable int departmentId ) {
		employeeService.deleteDeptToEmployee(employeeId, departmentId) ;
	}
	
	@PostMapping("bookOp/{patientId}")
	public void bookOpOfPatient(@RequestBody OP op, @PathVariable int patientId) {
		op.setPatientId(patientId);
		employeeService.bookOpForPatient(op);
	}
	
	@PostMapping("patients/op")
	public PatientOpDTO registerPatientAndBookOP(@RequestBody PatientOpDTO patientOpDTO) {

		return employeeService.registerPatientAndBookOP(patientOpDTO);
	}
	
	@PostMapping("verify")
	public Employee addNewEmployee(@RequestParam String token,@RequestParam String email, @RequestBody Employee employee) throws Exception {
		return employeeService.verifyAndAdd(token, email, employee ); 
	}
	
	@GetMapping("/search")
	public SearchDTO search(@RequestParam String value) {
		
		return employeeService.getSearchResults(value.toLowerCase());
		
		
	}
	
	
}
