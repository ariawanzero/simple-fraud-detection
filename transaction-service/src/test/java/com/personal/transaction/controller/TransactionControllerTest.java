package com.personal.transaction.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.personal.transaction.dto.request.TransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.enums.TransactionStatus;
import com.personal.transaction.exception.GlobalExceptionHandler;
import com.personal.transaction.exception.TransactionNotFoundException;
import com.personal.transaction.service.TransactionService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;
    
    @Test
    void shouldCreateTransaction() throws Exception {
        UUID id = UUID.randomUUID();

        TransactionResponse response = new TransactionResponse(
                id,
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US",
                TransactionStatus.PENDING,
                Instant.now()
        );

        when(transactionService.create(any()))
                .thenReturn(response);

        TransactionRequest request = new TransactionRequest(
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US"
        );

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value("ACC001"))
                .andExpect(jsonPath("$.cardId").value("CARD001"));
    }
    
    @Test
    void shouldReturnBadRequestWhenAccountIdIsBlank() throws Exception {
        TransactionRequest request = new TransactionRequest(
                "",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US"
        );

        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetTransactionById() throws Exception {
        UUID id = UUID.randomUUID();

        TransactionResponse response = new TransactionResponse(
                id,
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US",
                TransactionStatus.PENDING,
                Instant.now()
        );

        when(transactionService.getById(id))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.accountId").value("ACC001"));
    }
    
    @Test
    void shouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(transactionService.getById(id))
                .thenThrow(
                        new TransactionNotFoundException(id));

        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void shouldGetAllTransactions() throws Exception {
        TransactionResponse response = new TransactionResponse(
                UUID.randomUUID(),
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US",
                TransactionStatus.PENDING,
                Instant.now()
        );

        Page<TransactionResponse> page = new PageImpl<>(List.of(response));

        when(transactionService.getAll(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/v1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].accountId")
                        .value("ACC001"));
    }
}