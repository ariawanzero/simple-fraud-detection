package com.personal.transaction.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.transaction.dto.request.TransactionRequest;
import com.personal.transaction.dto.response.TransactionResponse;
import com.personal.transaction.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
		TransactionResponse response = transactionService.create(request);
		
		return ResponseEntity.created(URI.create(
				"/api/v1/transactions/" + response.id()))
				.body(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransactionResponse> getById(@PathVariable UUID id) {
		return ResponseEntity.ok(transactionService.getById(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<TransactionResponse>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		return ResponseEntity.ok(transactionService.getAll(page, size));
	}
}
