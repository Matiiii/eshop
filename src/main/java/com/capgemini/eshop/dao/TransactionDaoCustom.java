package com.capgemini.eshop.dao;

import java.util.Date;
import java.util.List;

import com.capgemini.eshop.dao.impl.TransactionSearchCriteria;
import com.capgemini.eshop.domain.TransactionEntity;
import com.capgemini.eshop.enums.Status;

public interface TransactionDaoCustom {

	List<TransactionEntity> findTransactionsByCustomerId(Long customerId);

	List<TransactionEntity> findTransactionsByCritria(TransactionSearchCriteria critera);

	Double sumOfAllTransactionsByCustomerId(Long customerId);

	Double sumOfAllTransactionsByCustomerIdAndStatus(Long customerId, Status status);

	Double sumOfAllTransactionsByStatus(Status status);

	Double calculateProfitInPeriod(Date from, Date to);

}
