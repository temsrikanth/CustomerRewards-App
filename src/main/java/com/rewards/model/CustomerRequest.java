package com.rewards.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerRequest {
    @NotBlank(message = "customerId is required")
    private String customerId;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    public String getCustomerId() { 
    	return customerId; 
    	}
    public void setCustomerId(String customerId) { 
    	this.customerId = customerId; 
    	}
    public String getName() { 
    	return name; 
    	}
    public void setName(String name) { 
    	this.name = name; 
    	}
    public String getEmail() { 
    	return email; 
    	}
    public void setEmail(String email) { 
    	this.email = email; 
    	}
	public CustomerRequest(String customerId, String name, String email) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
	}
	public CustomerRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
