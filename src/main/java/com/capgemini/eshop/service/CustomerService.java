package com.capgemini.eshop.service;

import com.capgemini.eshop.types.CustomerTO;

public interface CustomerService {

	CustomerTO saveNewCustomer(CustomerTO newCustomer);

	CustomerTO findCustomerById(Long id);

}
