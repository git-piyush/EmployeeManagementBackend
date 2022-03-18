package com.example.demo.dto;

import java.util.List;
import com.example.demo.model.Employee;

public class AllEmployeeResponseDto {

	private List<Employee> data;
	private int pageNo;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean last;
	public AllEmployeeResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AllEmployeeResponseDto(List<Employee> data, int pageNo, int pageSize, long totalElement, int totalPages,
			boolean last) {
		super();
		this.data = data;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalElement = totalElement;
		this.totalPages = totalPages;
		this.last = last;
	}
	public List<Employee> getData() {
		return data;
	}
	public void setData(List<Employee> data) {
		this.data = data;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalElement() {
		return totalElement;
	}
	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	
	
	
}
