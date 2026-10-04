package com.personal.transaction.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.personal.transaction.dto.request.TransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.entity.Transaction;
import com.personal.transaction.exception.TransactionNotFoundException;
import com.personal.transaction.mapper.TransactionMapper;
import com.personal.transaction.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCreateTransaction() {
        TransactionRequest request = new TransactionRequest(
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US"
        );

        Transaction entity = TransactionMapper.toEntity(request);
        entity.setId(UUID.randomUUID());

        when(repository.save(any(Transaction.class)))
                .thenReturn(entity);

        TransactionResponse response =
                service.create(request);

        assertNotNull(response);
        assertEquals("ACC001", response.accountId());

        verify(repository).save(any(Transaction.class));
    }
    
    @Test
    void shouldReturnTransactionById() {
	    UUID id = UUID.randomUUID();
	    
	    Transaction transaction = new Transaction();
	    transaction.setId(id);
	    transaction.setAccountId("ACC001");
	    transaction.setCardId("CARD001");
	    transaction.setAmount(BigDecimal.valueOf(100));
	    transaction.setCurrency("USD");
	    transaction.setMerchantName("Amazon");
	    transaction.setCountryCode("US");
	    
	    when(repository.findById(id)).thenReturn(Optional.of(transaction));
	    
	    TransactionResponse response = service.getById(id);
	    
	    assertNotNull(response);
	    assertEquals(id, response.id());
	    
	    verify(repository).findById(id);
    }
    
    @Test
    void shouldThrowExceptionWhenTransactionNotFound() {
	    UUID id = UUID.randomUUID();
	    
	    when(repository.findById(id)).thenReturn(Optional.empty());
	    
	    assertThrows(TransactionNotFoundException.class, () -> service.getById(id));
	    
	    verify(repository).findById(id);
	}
	    
    @Test
    void shouldReturnPageOfTransactions() {
	    Transaction transaction = new Transaction();
	    transaction.setId(UUID.randomUUID());
	    transaction.setAccountId("ACC001");
	    transaction.setCardId("CARD001");
	    transaction.setAmount(BigDecimal.valueOf(100));
	    transaction.setCurrency("USD");
	    transaction.setMerchantName("Amazon");
	    transaction.setCountryCode("US");
	    
	    Page<Transaction> page = new PageImpl<>(List.of(transaction));
	    
	    when(repository.findAll(any(Pageable.class))).thenReturn(page);
	    
	    Page<TransactionResponse> result =
	    service.getAll(0, 10);
	    
	    assertEquals(1, result.getTotalElements());
	    
	    verify(repository).findAll(any(Pageable.class));
    }
    
    @Test
    void shouldSaveTransactionOnce() {
        TransactionRequest request = new TransactionRequest(
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US"
        );

        Transaction saved = new Transaction();

        when(repository.save(any(Transaction.class))).thenReturn(saved);

        service.create(request);

        verify(repository, times(1)).save(any(Transaction.class));
    }
    
    @Test
    void shouldRequestCorrectPageable() {
        Page<Transaction> page = new PageImpl<>(Collections.emptyList());

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        service.getAll(2, 20);

        verify(repository).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();

        assertEquals(Sort.Direction.DESC, pageable.getSort().getOrderFor("createdAt").getDirection());
        assertEquals(2, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
    }
    
    @Test
    void shouldMapTransactionResponseCorrectly() {
        UUID id = UUID.randomUUID();

        TransactionRequest request = new TransactionRequest(
                "ACC001",
                "CARD001",
                BigDecimal.valueOf(100),
                "USD",
                "Amazon",
                "US"
        );

        Transaction saved = new Transaction();
        saved.setId(id);
        saved.setAccountId("ACC001");
        saved.setCardId("CARD001");
        saved.setAmount(BigDecimal.valueOf(100));
        saved.setCurrency("USD");
        saved.setMerchantName("Amazon");
        saved.setCountryCode("US");

        when(repository.save(any(Transaction.class))).thenReturn(saved);

        TransactionResponse response = service.create(request);

        assertAll(
                () -> assertEquals(id, response.id()),
                () -> assertEquals("ACC001", response.accountId()),
                () -> assertEquals("CARD001", response.cardId()),
                () -> assertEquals(BigDecimal.valueOf(100), response.amount()),
                () -> assertEquals("USD", response.currency()),
                () -> assertEquals("Amazon", response.merchantName()),
                () -> assertEquals("US", response.countryCode())
        );
    }
}