package com.capgemini.eshop.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.capgemini.eshop.domain.CustomerEntity;

@Transactional(value = TxType.REQUIRED)
public interface CustomerDaoCustom {

	List<CustomerEntity> find3CustomersWhoSpentMostInPeriod(Date from, Date to);

}
