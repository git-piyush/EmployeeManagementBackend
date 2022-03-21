package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.ResponseData;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@CrossOrigin(origins = "https://em-frontend.herokuapp.com/")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class AttachmentController {

	@Autowired
	EmployeeService employeeService;

	@PutMapping("/piyush/upload/{id}")
	public ResponseData uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws Exception {
		System.out.println("Hello");
		Employee employee = null;
		String downloadURl = "";
		employee = employeeService.saveAttachment(file, id);
		String empId = String.valueOf(employee.getId());

		downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(empId).toUriString();

		return new ResponseData(employee.getAttachment().getFileName(), downloadURl, file.getContentType(),
				file.getSize());
	}

	@GetMapping("/piyush/download/{empId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long empId) throws Exception {
		Employee employee = null;
		employee = employeeService.getAttachment(empId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(employee.getAttachment().getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + employee.getAttachment().getFileName() + "\"")
				.body(new ByteArrayResource(employee.getAttachment().getData()));

	}

}
