package com.personal.transaction.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.personal.transaction.enums.TransactionStatus;

public record TransactionResponse(
    UUID id,
    String accountId,
    String cardId,
    BigDecimal amount,
    String currency,
    String merchantName,
    String countryCode,
    TransactionStatus status,
    Instant createdAt
) {
}