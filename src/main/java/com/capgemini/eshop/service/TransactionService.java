package com.capgemini.eshop.service;

import java.util.Date;
import java.util.Set;

import com.capgemini.eshop.dao.impl.TransactionSearchCriteria;
import com.capgemini.eshop.enums.Status;
import com.capgemini.eshop.types.TransactionTO;

public interface TransactionService {

	TransactionTO saveNewTransaction(TransactionTO newOrder);

	TransactionTO findTransactionById(Long id);

	void removeTransaction(Long id);

	Long countRealizedTransactionsByCustomerId(Long id);

	TransactionTO updateTransaction(TransactionTO newTransaction);

	Set<TransactionTO> findTransaktionsByCustomerId(Long customerId);

	Set<TransactionTO> findTransactionByCriteria(TransactionSearchCriteria criteria);

	Double getSumCostOfAllTransatctionsByCustomerID(Long customerId);

	Double calculateProfitInPeriod(Date from, Date to);

	Double sumOfAllTransactionByCustomerIdAndStatus(Long customerId, Status status);

	Double sumOfAllTransactionsByStatus(Status status);

}
