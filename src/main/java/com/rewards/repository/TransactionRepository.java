package com.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.entity.TransactionEntity;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByCustomerCustomerId(String customerId);
    List<TransactionEntity> findByCustomerCustomerIdAndDateBetween(String customerId, LocalDate start, LocalDate end);
}
