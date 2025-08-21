package com.rewards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    @Min(value = 1, message = "amount must be > 0")
    private double amount;

    @Column(nullable=false)
    @NotNull(message = "date is required")
    private LocalDate date;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

	public TransactionEntity() {}

    public TransactionEntity(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { 
    	return id; 
    	}
    public void setId(Long id) { 
    	this.id = id; 
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
    public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public TransactionEntity(Long id, @Min(value = 1, message = "amount must be > 0") double amount,
			@NotNull(message = "date is required") LocalDate date, CustomerEntity customer) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.customer = customer;
	}
	
}
