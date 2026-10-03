package com.personal.transaction.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTransactionRequest(
    @NotBlank
    String accountId,
    
    @NotBlank
    String cardId,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal amount,

    @NotBlank
    String currency,

    @NotBlank
    String merchantName,

    @NotBlank
    String countryCode
) {
}