package com.rewards.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;

@DataJpaTest
class TransactionRepositoryTest {

	@Autowired
    private TransactionRepository transcationrepository;
	@Autowired
	private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindByCustomerId() {
    	
    	CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId("C002");
        customer.setName("Sree");
        customer.setEmail("Sree@example.com");
        customerRepository.save(customer);
        
        TransactionEntity transaction = new TransactionEntity( 150, LocalDate.of(2025, 1, 15));
        transaction.setCustomer(customer);
        transcationrepository.save(transaction);

        List<TransactionEntity> result = transcationrepository.findByCustomerCustomerId("C002");

        assertEquals(1, result.size());
        assertEquals("C002", result.get(0).getCustomer().getCustomerId());
        assertEquals(150, result.get(0).getAmount());
    }

    @Test
    void testFindByCustomerIdAndDateBetween_withinLast3Months() {
        LocalDate today = LocalDate.now();
        
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId("C002");
        customer.setName("Sree");
        customer.setEmail("Sree@example.com");
        customerRepository.save(customer);

        TransactionEntity recentTransaction = new TransactionEntity(200, LocalDate.of(2025, 8, 10));
        recentTransaction.setCustomer(customer);
        TransactionEntity oldTransaction = new TransactionEntity( 300, LocalDate.of(2025, 2, 20));
        oldTransaction.setCustomer(customer);
        
        transcationrepository.save(recentTransaction);
        transcationrepository.save(oldTransaction);

        
        LocalDate start = today.minusMonths(3);
        LocalDate end = today;

        List<TransactionEntity> result = transcationrepository.findByCustomerCustomerIdAndDateBetween("C002", start, end);

        assertEquals(1, result.size());
        assertEquals(200, result.get(0).getAmount());
        assertTrue(result.get(0).getDate().isAfter(start.minusDays(1)));
        assertTrue(result.get(0).getDate().isBefore(end.plusDays(1)));
    }

}
