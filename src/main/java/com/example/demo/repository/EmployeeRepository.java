package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {

	@Query("{'lastName':?0}")
	List<Employee> getEmployeeByLastName(String lastName);
	
}
