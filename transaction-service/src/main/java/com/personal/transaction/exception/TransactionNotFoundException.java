package com.personal.transaction.exception;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -774447168650703524L;

	public TransactionNotFoundException(UUID id) {
        super("Transaction not found with id: " + id);
    }
}