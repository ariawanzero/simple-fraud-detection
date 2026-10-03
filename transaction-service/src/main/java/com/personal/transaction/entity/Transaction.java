package com.personal.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions", schema = "fraud")
@Getter
@Setter
public class Transaction {

	@Id
	private UUID id;
	
	@Column(name = "account_id", nullable = false)
	private String accountId;
	
	@Column(name = "card_id", nullable = false)
	private String cardId;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false, length = 3)
	private String currency;
	
	@Column(name = "merchant_name", nullable = false)
	private String merchantName;
	
	@Column(name = "country_code", nullable = false)
	private String countryCode;
	
	@Column(nullable = false)
	private String status;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}
