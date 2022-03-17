package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "https://em-frontend.herokuapp.com/")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/piyush/{id1}/{id2}")
	public List<Employee> swapQuestions(@PathVariable Long id1, @PathVariable Long id2) {
		System.out.println(id1+" "+id2);
		employeeService.swapQuestions(id1, id2);
		List<Employee> emps = employeeService.getAllEmployees();
		return emps;
	}
	
	@GetMapping("/piyush")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/piyush/bytype/{lastName}")
	public List<Employee> getEmployeeByLastName(@Valid @PathVariable String lastName) {
		System.out.println(lastName);
		return employeeService.getEmployeeByLastName(lastName);
	}

	@PostMapping("/piyush")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {

		return employeeService.createEmployee(employee);
	}

	@GetMapping("/piyush/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/piyush/next/{id}")
	public Employee getNextQuestionById(@PathVariable Long id){
		//System.out.println(id);
		return employeeService.getNextQuestionById(id);
	}
	
	@GetMapping("/piyush/next/{type}/{id}")
	public Employee getNextQuestionByIdAndType(@PathVariable String type, @PathVariable Long id){
		return employeeService.getNextQuestionByIdAndType(type,id);
	}
	
	@GetMapping("/piyush/previous/{id}")
	public Employee getPreviousQuestionById(@PathVariable Long id){
		//System.out.println(id);
		return employeeService.getPreviousQuestionById(id);
	}
	
	@GetMapping("/piyush/previous/{type}/{id}")
	public Employee getPreviousQuestionByIdAndType( @PathVariable String type ,@PathVariable Long id){
		//System.out.println(id);
		return employeeService.getPreviousQuestionByIdAndType(type, id);
	}

	@PutMapping("/piyush/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		System.out.println(id);
		System.out.println(employeeDetails);
		return employeeService.updateEmployee(id, employeeDetails);
	}

	// delete employee rest api
	@DeleteMapping("/piyush/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		return employeeService.deleteEmployee(id);
	}

}
