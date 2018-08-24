package com.capgemini.eshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.domain.TransactionEntity;
import com.capgemini.eshop.enums.Status;

@Repository
public interface TransactionDao extends CrudRepository<TransactionEntity, Long>, TransactionDaoCustom {

	Long countByCustomerAndCurrentStatus(CustomerEntity customer, Status status);
}
