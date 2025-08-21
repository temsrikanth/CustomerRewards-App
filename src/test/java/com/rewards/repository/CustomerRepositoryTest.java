package com.rewards.repository;

import com.rewards.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindByCustomerId() {
    	
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId("C003");
        customer.setName("Krishna");
        customer.setEmail("Krishna@example.com");

        customerRepository.save(customer);

        Optional<CustomerEntity> found = customerRepository.findByCustomerId("C003");
        assertTrue(found.isPresent(), "Customer should be found by ID");
        assertEquals("Krishna", found.get().getName());
    }

    @Test
    void testExistsByCustomerId() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId("C002");
        customer.setName("Sree");
        customer.setEmail("Sree@example.com");

        customerRepository.save(customer);

        boolean exists = customerRepository.existsByCustomerId("C002");
        assertTrue(exists, "Customer should exist by ID");
    }

    @Test
    void testFindByCustomerId_NotFound() {
        Optional<CustomerEntity> found = customerRepository.findByCustomerId("INVALID");
        assertFalse(found.isPresent(), "Customer should not be found for invalid ID");
    }
}
