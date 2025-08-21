package com.rewards.controller;

import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;
import com.rewards.model.ApiResponse;
import com.rewards.model.CustomerRequest;
import com.rewards.model.RewardsResponse;
import com.rewards.model.TransactionRequest;
import com.rewards.service.RewardsService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "*")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<CustomerEntity>> addCustomer(@Valid @RequestBody CustomerRequest request) {
    	CustomerEntity saved = rewardsService.addCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Customer created", saved));
    }

    @PostMapping("/transactions")
    public ResponseEntity<ApiResponse<List<TransactionEntity>>> addTransactions(@Valid @RequestBody List<TransactionRequest> request) {
        List<TransactionEntity> saved = rewardsService.saveTransactions(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Transactions saved", saved));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<RewardsResponse>> getRewards(@PathVariable String customerId) {
        RewardsResponse rewards = rewardsService.calculateRewards(customerId);
        return ResponseEntity.ok(ApiResponse.success("OK", rewards));
    }
}
