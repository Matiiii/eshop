package com.capgemini.eshop.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.eshop.dao.CustomerDao;
import com.capgemini.eshop.dao.TransactionDao;
import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.exceptions.Message;
import com.capgemini.eshop.mappers.CustomerMapper;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.service.TransactionService;
import com.capgemini.eshop.types.CustomerTO;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	TransactionDao transactionRepository;

	@Autowired
	CustomerDao customerRepository;

	@Autowired
	TransactionService transactionService;

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

	@Override
	public CustomerTO updateCustomer(CustomerTO customer) {
		CustomerEntity customerBeforeUpdate = customerRepository.findOne(customer.getId());
		CustomerEntity customerToUpdate = customerMapper.map(customer);
		customerToUpdate.setOrders(customerBeforeUpdate.getOrders());

		CustomerEntity savedCustomer = customerRepository.save(customerToUpdate);

		return customerMapper.map(savedCustomer);
	}

	@Override
	public void removeCustomer(Long id) {

		if (customerRepository.exists(id)) {

			customerRepository.findOne(id).getOrders().stream().mapToLong(transaction -> transaction.getId())
					.forEach(transactionId -> transactionService.removeTransaction(transactionId));

			customerRepository.delete(id);

		} else {
			throw new RuntimeException("Customer not exist");
		}

	}

	@Override
	public List<CustomerTO> find3CustomersWhoSpentMostInPeriod(Date from, Date to) {

		return customerMapper.map2To(customerRepository.find3CustomersWhoSpentMostInPeriod(from, to));
	}

}
