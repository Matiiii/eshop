package com.capgemini.eshop.dao;

import java.util.List;

import com.capgemini.eshop.domain.TransactionEntity;

public interface TransactionDaoCustom {

	List<TransactionEntity> findTransactionByCustomerId(Long customerId);

}
