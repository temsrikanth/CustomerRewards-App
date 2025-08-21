package com.rewards.service.impl;

import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;
import com.rewards.exception.BadRequestException;
import com.rewards.exception.NotFoundException;
import com.rewards.model.CustomerRequest;
import com.rewards.model.RewardsResponse;
import com.rewards.model.TransactionRequest;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardsService;
import com.rewards.util.DateRange;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RewardsServiceImpl implements RewardsService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public RewardsServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public CustomerEntity addCustomer(CustomerRequest request) {
        if (request == null) {
        	throw new BadRequestException("Customer payload cannot be null");
        }
        if (request.getCustomerId() == null || request.getCustomerId().trim().isEmpty()) {
            throw new BadRequestException("customerId is required");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("name is required");
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            throw new BadRequestException("valid email is required");
        }
        
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(request.getCustomerId());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());

        return customerRepository.save(customer);
    }

    @Override
    public List<TransactionEntity> saveTransactions(List<TransactionRequest> request) {
        if (request == null || request.isEmpty()) {
            throw new BadRequestException("Transaction list cannot be empty");
        }

        List<TransactionEntity> transactions=new ArrayList<>();
        for (TransactionRequest req : request) {
            if (req.getCustomerId() == null || req.getCustomerId().trim().isEmpty()) {
                throw new BadRequestException("customerId is required for each transaction");
            }
            if (!customerRepository.existsByCustomerId(req.getCustomerId())) {
                throw new NotFoundException("Customer not found: " + req.getCustomerId());
            }
            if (req.getAmount() <= 0) {
                throw new BadRequestException("amount must be positive");
            }
            if (req.getDate() == null) {
                throw new BadRequestException("date is required");
            }
            if (req.getDate().isAfter(LocalDate.now())) {
                throw new BadRequestException("date cannot be in the future");
            }
            
            CustomerEntity customer = customerRepository.findByCustomerId(req.getCustomerId())
                    .orElseThrow(() -> new NotFoundException("Customer not found: " + req.getCustomerId()));
        
            TransactionEntity transaction = new TransactionEntity();
            transaction.setCustomer(customer);
            transaction.setAmount(req.getAmount());
            transaction.setDate(req.getDate());
            transactions.add(transaction);
        
        }
        return transactionRepository.saveAll(transactions);
    }

    @Override
    public RewardsResponse calculateRewards(String customerId) {
        if (customerId == null || customerId.trim().isEmpty())
            throw new BadRequestException("customerId is required");

        if (!customerRepository.existsByCustomerId(customerId))
            throw new NotFoundException("Customer not found: " + customerId);

        DateRange range = DateRange.lastNMonthsInclusive(3);
        List<TransactionEntity> transactions = transactionRepository.findByCustomerCustomerIdAndDateBetween(customerId, range.getStart(), range.getEnd());

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int total = 0;

        for (TransactionEntity tx : transactions) {
        	
            int points = getPoints(tx.getAmount());
            String month = tx.getDate().getMonth().toString();
            monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
            total += points;
        }

        RewardsResponse resp = new RewardsResponse();
        resp.setCustomerId(customerId);
        resp.setMonthlyPoints(monthlyPoints);
        resp.setTotalPoints(total);
        return resp;
    }

    
    private int getPoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += 50; 
            points += (int) ((amount - 100) * 2);
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}
