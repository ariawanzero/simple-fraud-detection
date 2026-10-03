package com.personal.transaction.mapper;

import com.personal.transaction.dto.request.CreateTransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.entity.Transaction;

public final class TransactionMapper {

	private TransactionMapper() {
	}
	
	public static Transaction toEntity(CreateTransactionRequest request) {
		Transaction transaction = new Transaction();
		
		transaction.setAccountId(request.accountId());
		transaction.setCardId(request.cardId());
		transaction.setAmount(request.amount());
		transaction.setCurrency(request.currency());
		transaction.setMerchantName(request.merchantName());
		transaction.setCountryCode(request.countryCode());
		
		return transaction;
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
