package com.capgemini.eshop.service;

import java.util.Set;

import com.capgemini.eshop.types.TransactionTO;

public interface TransactionService {

	TransactionTO saveNewTransaction(TransactionTO newOrder);

	TransactionTO findTransactionById(Long id);

	void removeTransaction(Long id);

	Long countRealizedTransactionsByCustomerId(Long id);

	TransactionTO updateTransaction(TransactionTO newTransaction);

	Set<TransactionTO> findTransaktionsByCustomerId(Long customerId);

}
