package com.chrisali.model;

import lombok.Data;

@Data
public class ErrorInfo {
	
	private String message;
	
	private String statusCode;
	
	private String html;
}