package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface SequenceGenerator {

	public int getSequenceNumber(String sequenceName);
	
}
