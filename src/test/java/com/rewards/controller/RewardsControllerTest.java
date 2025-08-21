package com.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.entity.CustomerEntity;
import com.rewards.entity.TransactionEntity;
import com.rewards.exception.NotFoundException;
import com.rewards.model.ApiResponse;
import com.rewards.model.CustomerRequest;
import com.rewards.model.RewardsResponse;
import com.rewards.model.TransactionRequest;
import com.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

	@Autowired
    private MockMvc mockMvc;
	@Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private RewardsService rewardsService;

    

    @Test
    public void testAddCustomer() throws Exception {
    	CustomerRequest request = new CustomerRequest("C001","Srikanth","srikanth@example.com");
    	CustomerEntity savedEntity = new CustomerEntity("C001", "Srikanth", "srikanth@example.com");

        //Post- /customers- validation success
        
        when(rewardsService.addCustomer(any(CustomerRequest.class))).thenReturn(savedEntity);

        mockMvc.perform(post("/api/rewards/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("Customer created")))
                .andExpect(jsonPath("$.data.customerId", is("C001")));
    }
    
    // Post- /customers- validation failure
    @Test
    void testAddCustomer_validationError() throws Exception {
        CustomerRequest request = new CustomerRequest();

        mockMvc.perform(post("/api/rewards/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.message", containsString("One or more fields are invalid.")));
    }
    // POST /transactions - success
    @Test
    void testAddTransactions_success() throws Exception {
        TransactionRequest tr1 = new TransactionRequest("C001", 120.0, LocalDate.of(2025, 8, 10));
        TransactionRequest tr2 = new TransactionRequest("C001", 200.0, LocalDate.of(2025, 8, 11));
        CustomerEntity customer= new CustomerEntity("C001", "Srikanth", "srikanth@example.com");

        List<TransactionEntity> saved = List.of(
                new TransactionEntity(1L, 120.0, LocalDate.of(2025, 8, 10),customer),
                new TransactionEntity(2L, 200.0, LocalDate.of(2025, 8, 11),customer));

        when(rewardsService.saveTransactions(anyList())).thenReturn(saved);

        mockMvc.perform(post("/api/rewards/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(tr1, tr2))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("Transactions saved")))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    // GET- /rewards/{customerId} - success
    @Test
    void testGetRewards_success() throws Exception {
        RewardsResponse mockResponse = new RewardsResponse();
        mockResponse.setTotalPoints(300);
        mockResponse.setMonthlyPoints(Map.of("2025-06", 100,
        		"2025-07", 150,
        	    "2025-08", 200));

        when(rewardsService.calculateRewards("C001")).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards/C001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.message", is("OK")))
                .andExpect(jsonPath("$.data.monthlyPoints['2025-06']", is(100)))
                .andExpect(jsonPath("$.data.monthlyPoints['2025-07']", is(150)))
                .andExpect(jsonPath("$.data.monthlyPoints['2025-08']", is(200)));
    }

    // GET /rewards/{customerId} - not found
    @Test
    void testGetRewards_notFound() throws Exception {
        when(rewardsService.calculateRewards("INVALID"))
                .thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/api/rewards/INVALID"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("Customer not found")));
    }


}
