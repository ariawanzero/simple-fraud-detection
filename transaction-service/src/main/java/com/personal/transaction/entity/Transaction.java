package com.personal.transaction.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.personal.transaction.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions", schema = "fraud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "account_id", nullable = false)
	private String accountId;
	
	@Column(name = "card_id", nullable = false)
	private String cardId;
	
	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	
	@Column(nullable = false, length = 3)
	private String currency;
	
	@Column(name = "merchant_name", nullable = false)
	private String merchantName;
	
	@Column(name = "country_code", nullable = false)
	private String countryCode;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;
	
	@PrePersist
	public void prePersist() {
	    createdAt = Instant.now();
	    updatedAt = Instant.now();

	    if (status == null) {
	        status = TransactionStatus.PENDING;
	    }
	}

	@PreUpdate
	public void preUpdate() {
	    updatedAt = Instant.now();
	}
}
