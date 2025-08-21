package com.rewards.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TransactionRequest {

    @NotNull(message = "customerId is required")
    private String customerId;

    @Min(value = 1, message = "Amount must be greater than zero")
    private double amount;

    @NotNull(message = "date is required")
    private LocalDate date;

    public String getCustomerId() { 
    	return customerId; 
    	}
    public void setCustomerId(String customerId) { 
    	this.customerId = customerId; 
    	}
    public double getAmount() { 
    	return amount; 
    	}
    public void setAmount(double amount) { 
    	this.amount = amount; 
    	}
    public LocalDate getDate() { 
    	return date; 
    	}
    public void setDate(LocalDate date) { 
    	this.date = date; 
    	}
	public TransactionRequest(@NotNull(message = "customerId is required") String customerId,
			@Min(value = 1, message = "Amount must be greater than zero") double amount,
			@NotNull(message = "date is required") LocalDate date) {
		super();
		this.customerId = customerId;
		this.amount = amount;
		this.date = date;
	}
	public TransactionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
