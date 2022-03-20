package com.example.demo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.AllEmployeeResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Attachment;
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
	public Employee getNextQuestionById(Long id) {
		List<Employee> employess = employeeRepository.getNextQuestionById(id);
		Employee employee = null;
		if (employess.isEmpty()) {
			employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		} else {
			employee = employess.get(0);
		}

		return employee;

	}

	@Override
	public Employee getPreviousQuestionById(Long id) {
		List<Employee> employess = employeeRepository.getPreviousQuestionById(id);
		Employee employee = null;
		if (employess.isEmpty()) {
			employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		} else {
			employee = employess.get(employess.size()-1);
		}

		return employee;
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
		System.out.println("Hello world!!");
		return employeeRepository.getEmployeeByLastName(lastName);
	}

	@Override
	public Employee getNextQuestionByIdAndType(String type, Long id) {
		System.out.println(type);
		System.out.println(id);
		List<Employee> employess = employeeRepository.getNextQuestionByIdAndType(type,id);
		
		System.out.println(employess.size());
		Employee employee = null;
		if (employess.isEmpty()) {
			employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		} else {
			employee = employess.get(0);
		}

		return employee;
	}

	@Override
	public Employee getPreviousQuestionByIdAndType(String type, Long id) {
		List<Employee> employess = employeeRepository.getPreviousQuestionByIdAndType(type, id);
		Employee employee = null;
		if (employess.isEmpty()) {
			employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		} else {
			employee = employess.get(employess.size()-1);
		}

		return employee;
	}

	@Override
	public void swapQuestions(Long id1, Long id2) {
		
		Employee employee1 = employeeRepository.findById(id1)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id1));
		
		Employee employee2 = employeeRepository.findById(id2)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id2));
		
		System.out.println(employee1);
		System.out.println(employee2);
		
		Long empId1 = employee1.getId();
		
		employee1.setId(employee2.getId());
		employee2.setId(empId1);
		
		System.out.println(employee1);
		System.out.println(employee2);
		
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);

		
	}

	@Override
	public AllEmployeeResponseDto getAllEmployeesPagination(int pageSize, int pageNo, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable page = PageRequest.of(pageNo, pageSize, sort);
		Page<Employee> employess = employeeRepository.findAll(page);
		
		List<Employee> employeeList = employess.getContent();
		
		AllEmployeeResponseDto allEmployeeResponseDto = new AllEmployeeResponseDto();
		
		allEmployeeResponseDto.setData(employeeList);
		allEmployeeResponseDto.setPageNo(employess.getNumber());
		allEmployeeResponseDto.setPageSize(employess.getSize());
		allEmployeeResponseDto.setTotalPages(employess.getTotalPages());
		allEmployeeResponseDto.setTotalElement(employess.getTotalElements());
		allEmployeeResponseDto.setLast(employess.isLast());
		
		return allEmployeeResponseDto;
	}

	@Override
	public Employee saveAttachment(MultipartFile file, Long id) throws Exception {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                + fileName);
            }

            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            
            employee.setAttachment(attachment);
            
            return employeeRepository.save(employee);

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }

	}

	@Override
	public Employee getAttachment(Long empId) throws Exception {
		return employeeRepository
                .findById(empId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + empId));
	}

}
