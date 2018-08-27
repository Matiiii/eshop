package com.capgemini.eshop.service;

import java.util.Date;
import java.util.List;

import com.capgemini.eshop.types.CustomerTO;

public interface CustomerService {

	CustomerTO saveNewCustomer(CustomerTO newCustomer);

	CustomerTO findCustomerById(Long id);

	CustomerTO updateCustomer(CustomerTO customerToUpdate);

	void removeCustomer(Long id);

	List<CustomerTO> find3CustomersWhoSpentMostInPeriod(Date from, Date to);

}
