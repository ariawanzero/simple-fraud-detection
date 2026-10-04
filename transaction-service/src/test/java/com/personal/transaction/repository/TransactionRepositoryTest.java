package com.personal.transaction.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.personal.transaction.entity.Transaction;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {
	
	@Autowired
	private TransactionRepository repository;
	
	@Test
	void shouldSaveTransaction() {
	    Transaction transaction = new Transaction();

	    transaction.setAccountId("ACC001");
	    transaction.setCardId("CARD001");
	    transaction.setAmount(BigDecimal.valueOf(100));
	    transaction.setCurrency("USD");
	    transaction.setMerchantName("Amazon");
	    transaction.setCountryCode("US");

	    Transaction saved = repository.save(transaction);

	    assertNotNull(saved.getId());
	    assertEquals("ACC001", saved.getAccountId());
	}
	
	@Test
	void shouldFindTransactionById() {
	    Transaction transaction = new Transaction();

	    transaction.setAccountId("ACC001");
	    transaction.setCardId("CARD001");
	    transaction.setAmount(BigDecimal.valueOf(100));
	    transaction.setCurrency("USD");
	    transaction.setMerchantName("Amazon");
	    transaction.setCountryCode("US");

	    Transaction saved = repository.save(transaction);

	    Optional<Transaction> result =
	            repository.findById(saved.getId());

	    assertTrue(result.isPresent());
	    assertEquals("ACC001", result.get().getAccountId());
	}


}
