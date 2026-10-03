package com.personal.transaction.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public TransactionResponse create(TransactionRequest request) {
		Transaction transaction = TransactionMapper.toEntity(request);
		
		Transaction saved = repository.save(transaction);

		return TransactionMapper.toResponse(saved);
	}
	
	@Transactional(readOnly = true)
	public TransactionResponse getById(UUID id) {
		Transaction transaction = repository.findById(id)
									.orElseThrow(() -> new TransactionNotFoundException(id));
		
		return TransactionMapper.toResponse(transaction);
	}
	
	@Transactional(readOnly = true)
	public Page<TransactionResponse> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		
		return repository.findAll(pageable).map(TransactionMapper::toResponse);
	}
}
