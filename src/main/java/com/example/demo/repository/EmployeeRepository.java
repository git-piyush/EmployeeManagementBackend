package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Long> {

	//@Query("{'lastName':?0}")
	List<Employee> getEmployeeByLastName(String lastName);
	
	@Query("{'_id':{$gt:?0} }")
	List<Employee> getNextQuestionById(Long Id);
	
	@Query("{'_id':{$lt:?0} }")
	List<Employee> getPreviousQuestionById(Long Id);
	
	@Query("{$and:[{'lastName':?0},{'_id':{$gt:?1}}]}")
	List<Employee> getNextQuestionByIdAndType(String lastName, Long Id);

	@Query("{$and:[{'lastName':?0},{'_id':{$lt:?1}}]}")
	List<Employee> getPreviousQuestionByIdAndType(String lastName, Long id);
	
}
