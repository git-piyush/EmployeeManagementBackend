package com.example.demo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SequenceGenerator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();

	}

	@Override
	public Employee createEmployee(Employee employee) {
		employee.setId(sequenceGenerator.getSequenceNumber(employee.SEQUENCE_NAME));
		return employeeRepository.save(employee);

	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(employee);

	}

	@Override
	public ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}

	@Override
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);

	}

	@Override
	public List<Employee> getEmployeeByLastName(String lastName) {
		return employeeRepository.getEmployeeByLastName(lastName);
	}

}
