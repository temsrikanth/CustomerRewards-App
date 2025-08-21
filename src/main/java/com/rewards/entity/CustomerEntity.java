package com.rewards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "customer_id",nullable=false, unique=true, length = 40)
    @NotBlank(message = "customerId is required")
    private String customerId;
    
	@Column(nullable=false, length = 120)
    @NotBlank(message = "name is required")
    private String name;

    @Column(nullable=false, unique=true)
    @Email @NotBlank(message = "email is required")
    private String email;

    public CustomerEntity() {}

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
    
    public CustomerEntity(@NotBlank(message = "customerId is required") String customerId,
			@NotBlank(message = "name is required") String name,
			@Email @NotBlank(message = "email is required") String email) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
	}
}
