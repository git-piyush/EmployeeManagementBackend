package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AllEmployeeResponseDto;
import com.example.demo.model.Employee;

@Service
public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public Employee createEmployee(Employee employee);

	public ResponseEntity<Employee> getEmployeeById(Long id);
	
	public ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails);
	
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id);
	
	public List<Employee> getEmployeeByLastName(String lastName);

	public Employee getNextQuestionById(Long id);

	public Employee getPreviousQuestionById(Long id);

	public Employee getNextQuestionByIdAndType(String type, Long id);

	public Employee getPreviousQuestionByIdAndType(String type, Long id);

	public void swapQuestions(Long id1, Long id2);

	public AllEmployeeResponseDto getAllEmployeesPagination(int pageSize, int pageNo, String sortBy, String sortDir);

}
