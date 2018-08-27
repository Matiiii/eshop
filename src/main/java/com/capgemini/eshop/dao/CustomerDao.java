package com.capgemini.eshop.dao;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.CustomerEntity;

@Transactional(value = TxType.REQUIRED)
@Repository
public interface CustomerDao extends CrudRepository<CustomerEntity, Long>, CustomerDaoCustom {

}
