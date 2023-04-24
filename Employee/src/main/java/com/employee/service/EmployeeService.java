package com.employee.service;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.employee.dto.Department;
import com.employee.dto.OP;
import com.employee.dto.PatientOpDTO;
import com.employee.dto.SearchDTO;
import com.employee.entity.Employee;
import com.employee.entity.EmployeeDepartment;
import com.employee.repository.EmployeeDepartmentRepository;
import com.employee.repository.EmployeeRepo;

import jakarta.ws.rs.core.UriBuilder;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeDepartmentRepository employeeDetailsRepository;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	@Autowired 
	private JavaMailSender mailSender ;
	
	public Employee addEmployee(Employee employee) {
		UUID uuid = UUID.randomUUID() ;
		employee.setTokenExpired(false);
		employee.setToken(uuid.toString());
		employee = employeeRepo.save(employee);
		
		
//		UriBuilder uriBuilder = UriBuilder.newInstance()
//									.host("localhost")
//									.port(3000)
//									.queryParam("token", employee.getToken())
//									.queryParam("email", employee.getEmail()) ;

		String body = """ 
					Your account has been created but yet to be verified.
					
					 Please click on the below link to verify and fill the mandatory details.
					 
					 #link#
				""".replace("#link#", "http://localhost:3000/verify/register?token="+uuid.toString()+"&email="+employee.getEmail());
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("minooshreddykallam@gmail.com");
		simpleMailMessage.setTo(employee.getEmail());
		simpleMailMessage.setSubject("Verify your email address");
		simpleMailMessage.setText(body);
		
		mailSender.send(simpleMailMessage);
		
		return employee;
	}
	
	public Employee updateEmployee(Employee employee) {
		return employeeRepo.save(employee);
	}
	
	public Employee getEmployee(int id) {
		Optional<Employee> optional = employeeRepo.findById(id);
		return optional.isEmpty() ? null : optional.get() ;
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepo.findAll();
	}
	
	public void deleteEmployee(int id) {	
		employeeRepo.deleteById(id);
	}
	
	
	
	public void addDeptToEmployee(int employeeId, int departmentId) {
		EmployeeDepartment employeeDetails = new EmployeeDepartment();
		employeeDetails.setDepartmentId(departmentId);
		employeeDetails.setEmployeeId(employeeId);
		employeeDetailsRepository.save(employeeDetails);
	}
	
	public void deleteDeptToEmployee(int employeeId, int departmentId) {
		employeeDetailsRepository.removeEmployeesDepartment(employeeId, departmentId);
	}
	

	public List<Department> getDepartmentsOfEmployee(int id) {
		
		List<EmployeeDepartment> employeeDetails = employeeDetailsRepository.findAllByEmployeeId(id);
		List<Department> employeeDepartments = new ArrayList<>();
		
		for(EmployeeDepartment employeeDetail : employeeDetails)
			employeeDepartments.add(restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+employeeDetail.getDepartmentId(), Department.class ) );
		
		return employeeDepartments;
	}

	public OP bookOpForPatient(OP op) {

		op = restTemplate.postForObject("http://PATIENT-SERVICE/patients/", op, OP.class);
		return op;
	}

	public PatientOpDTO registerPatientAndBookOP(PatientOpDTO patientOpDTO) {
		patientOpDTO = restTemplate.postForObject("http://PATIENT-SERVICE/patients/ops", patientOpDTO, PatientOpDTO.class);
		return patientOpDTO;
	}

	public Employee verifyAndAdd( String token, String email, Employee updatedEmployee) throws Exception {
		
		Employee employee = employeeRepo.findByEmail(email);
		if(token.equals(employee.getToken()) && !employee.isTokenExpired()) {
			updatedEmployee.setToken(null);
			updatedEmployee.setTokenExpired(true);
			updatedEmployee.setId(employee.getId());
			updatedEmployee.setEmail(email);
			updatedEmployee.setPrimeDeptId(employee.getPrimeDeptId());
			
			return updateEmployee(updatedEmployee);
		}
		else
			throw new Exception("Invalid Token or token is expired");
	}

	public SearchDTO getSearchResults(String value) {
		SearchDTO searchDTO = new SearchDTO();
		
		List<Employee> employees = employeeRepo.findAll().stream().filter(e -> (e.getLastName()+","+ e.getFirstName()).toLowerCase().contains(value)).toList();
		SearchDTO departmentSearch =  restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/search?value="+value, SearchDTO.class);
		
		searchDTO.setEmployees(employees);
		if(departmentSearch != null)
			searchDTO.setDepartments(departmentSearch.getDepartments());
		
		return searchDTO;
	}
	
}
