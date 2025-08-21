package com.rewards.service;

import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;
import com.rewards.model.CustomerRequest;
import com.rewards.model.RewardsResponse;
import com.rewards.model.TransactionRequest;

import java.util.List;

public interface RewardsService {
    CustomerEntity addCustomer(CustomerRequest request);
    List<TransactionEntity> saveTransactions(List<TransactionRequest> request);
    RewardsResponse calculateRewards(String customerId);
}
