package com.personal.transaction.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.personal.transaction.dto.request.TransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.entity.Transaction;
import com.personal.transaction.exception.TransactionNotFoundException;
import com.personal.transaction.mapper.TransactionMapper;
import com.personal.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository repository;
	
	public TransactionResponse create(TransactionRequest request) {
		Transaction transaction = TransactionMapper.toEntity(request);
		
		Transaction saved = repository.save(transaction);

		return TransactionMapper.toResponse(saved);
	}
	
	public TransactionResponse getById(UUID id) {
		Transaction transaction = repository.findById(id)
									.orElseThrow(() -> new TransactionNotFoundException(id));
		
		return TransactionMapper.toResponse(transaction);
	}
	
	public Page<TransactionResponse> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		return repository.findAll(pageable).map(TransactionMapper::toResponse);
	}
}
