package com.capgemini.eshop.dao;

import java.util.List;

import com.capgemini.eshop.domain.CustomerEntity;

public interface CustomerDaoCustom {

	List<CustomerEntity> find3CustomerWhoSpendMostInYear();

}
