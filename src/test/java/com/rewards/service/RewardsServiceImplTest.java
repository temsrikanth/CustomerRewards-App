package com.rewards.service;

import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;
import com.rewards.exception.BadRequestException;
import com.rewards.exception.NotFoundException;
import com.rewards.model.CustomerRequest;
import com.rewards.model.RewardsResponse;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.impl.RewardsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RewardsServiceImplTest {

	private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private RewardsServiceImpl service;

    @BeforeEach
    void setup() {
        customerRepository = mock(CustomerRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        service = new RewardsServiceImpl(customerRepository, transactionRepository);
    }

    @Test
    void testCalculateRewards_success() {
        when(customerRepository.existsByCustomerId("C001")).thenReturn(true);
        
        CustomerEntity customer = new CustomerEntity("C001","Srikanth","srikanth@test.com");

        TransactionEntity t1 = new TransactionEntity(120, LocalDate.of(2025, 8, 10));
        t1.setCustomer(customer);
        TransactionEntity t2 = new TransactionEntity(60, LocalDate.of(2025, 2, 20));
        t2.setCustomer(customer);
        
        List<TransactionEntity> txs = Arrays.asList(t1,t2);

        when(transactionRepository.findByCustomerCustomerIdAndDateBetween(eq("C001"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(txs);

        RewardsResponse response = service.calculateRewards("C001");

        assertEquals("C001", response.getCustomerId());
        assertEquals(100, response.getTotalPoints());
        assertTrue(response.getMonthlyPoints().values().stream().mapToInt(Integer::intValue).sum() > 0);

        verify(transactionRepository, times(1)).findByCustomerCustomerIdAndDateBetween(eq("C001"), any(), any());
    }
    
    //->customerId missing
    @Test
    void testCalculateRewards_missingCustomerId() {
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> service.calculateRewards(" "));
        assertEquals("customerId is required", ex.getMessage());
    }

    //->customer not exists
    @Test
    void testCalculateRewards_customerNotFound() {
        when(customerRepository.existsByCustomerId("X999")).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> service.calculateRewards("X999"));
        assertTrue(ex.getMessage().contains("Customer not found"));
    }

    @Test
    void testAddCustomer_success() {
        CustomerRequest req = new CustomerRequest("C002","Sree","Sree@test.com");
        
        CustomerEntity customer = new CustomerEntity("C002","Sree","Sree@test.com");
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);

        CustomerEntity saved = service.addCustomer(req);

        assertEquals("C002", saved.getCustomerId());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    //->invalid email
    @Test
    void testAddCustomer_invalidEmail() {
        CustomerRequest request = new CustomerRequest();
        request.setCustomerId("C003");
        request.setName("Krishna");
        request.setEmail("Krishna");

        assertThrows(BadRequestException.class, () -> service.addCustomer(request));
    }
}
