package com.capgemini.eshop.service.impl;

import javax.transaction.Transactional;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.eshop.dao.CustomerDao;
import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.exceptions.Message;
import com.capgemini.eshop.mappers.CustomerMapper;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.types.CustomerTO;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao customerRepository;

	@Autowired
	CustomerMapper customerMapper;

	@Override
	public CustomerTO saveNewCustomer(CustomerTO newCustomer) {
		Preconditions.checkNotNull(newCustomer, Message.EMPTY_OBJECT);

		CustomerEntity customerToSave = customerMapper.map(newCustomer);
		CustomerEntity savedCustomer = customerRepository.save(customerToSave);

		return customerMapper.map(savedCustomer);
	}

	@Override
	public CustomerTO findCustomerById(Long id) {
		CustomerEntity selectedCustomer = customerRepository.findOne(id);
		return customerMapper.map(selectedCustomer);
	}

}
