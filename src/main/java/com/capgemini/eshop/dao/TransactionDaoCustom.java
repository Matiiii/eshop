package com.capgemini.eshop.dao;

import java.util.List;

import com.capgemini.eshop.dao.impl.TransactionSearchCriteria;
import com.capgemini.eshop.domain.TransactionEntity;

public interface TransactionDaoCustom {

	List<TransactionEntity> findTransactionByCustomerId(Long customerId);

	List<TransactionEntity> findTransactionByCritria(TransactionSearchCriteria critera);

}
