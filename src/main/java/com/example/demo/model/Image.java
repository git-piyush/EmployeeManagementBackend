package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Image {

	@Id
    private String id;
    private String name; // file name
    private Date createdtime; // upload time
    private long content; // file content
    private String contenttype; // file type
    private long size; // file size
	
}
