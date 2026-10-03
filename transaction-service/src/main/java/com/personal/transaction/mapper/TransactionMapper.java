package com.personal.transaction.mapper;

import com.personal.transaction.dto.request.TransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.entity.Transaction;

public final class TransactionMapper {

	private TransactionMapper() {
	}
	
	public static Transaction toEntity(TransactionRequest request) {
		
		return Transaction.builder()
				.accountId(request.accountId())
				.cardId(request.cardId())
				.amount(request.amount())
				.currency(request.currency())
				.merchantName(request.merchantName())
				.countryCode(request.countryCode())
				.build();
	}
	
	public static TransactionResponse toResponse(Transaction transaction) {
		return new TransactionResponse(
			transaction.getId(),
			transaction.getAccountId(),
			transaction.getCardId(),
			transaction.getAmount(),
			transaction.getCurrency(),
			transaction.getMerchantName(),
			transaction.getCountryCode(),
			transaction.getStatus(),
			transaction.getCreatedAt()
		);
	}
}
