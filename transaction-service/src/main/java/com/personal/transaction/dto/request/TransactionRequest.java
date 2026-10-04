package com.personal.transaction.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TransactionRequest(
    @NotBlank
    String accountId,
    
    @NotBlank
    String cardId,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal amount,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    String currency,

    @NotBlank
    String merchantName,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    String countryCode
) {
}