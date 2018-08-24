package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.eshop.dao.ProductDao;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.service.TransactionService;
import com.capgemini.eshop.types.CustomerTO;
import com.capgemini.eshop.types.ProductTO;
import com.capgemini.eshop.types.TransactionTO;

@RunWith(SpringRunner.class)
@SpringBootTest // (properties = "spring.profiles.active=mysql")
public class CustomerServiceTest {

	@Autowired
	DataCreator dataCreator;

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	ProductService productService;

	@Autowired
	ProductDao productRepository;

	@Transactional
	@Test
	public void shouldGetCustomerById() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		// when

		CustomerTO selectedCustomer = customerService.findCustomerById(savedCustomer.getId());

		// then
		assertNotNull(selectedCustomer);
		assertEquals(savedCustomer.getId(), selectedCustomer.getId());
		assertEquals(savedCustomer.getPersonalDetail(), selectedCustomer.getPersonalDetail());

	}

	@Transactional
	@Test
	public void shouldUpdateCustomer() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		savedCustomer.getPersonalDetail().setName("Marcin");
		// when

		CustomerTO updatedCustomer = customerService.updateCustomer(savedCustomer);

		// then
		assertNotNull(updatedCustomer);
		assertEquals(savedCustomer.getId(), updatedCustomer.getId());
		assertEquals("Marcin", updatedCustomer.getPersonalDetail().getName());

	}

	@Transactional
	@Test
	public void shouldRemoveCustomer() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		savedCustomer.getPersonalDetail().setName("Marcin");
		// when

		customerService.removeCustomer(savedCustomer.getId());

		CustomerTO removedCustomer = customerService.findCustomerById(savedCustomer.getId());
		// then
		assertNull(removedCustomer);

	}

	@Transactional
	@Test
	public void shouldRemoveCustomerAndCustomersTransaction() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMajty();
		ProductTO savedProduct2 = dataCreator.saveNewProductMlotek();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		// when

		customerService.removeCustomer(savedCustomer.getId());

		boolean isTransactionInProduct = productService.findProductById(savedProduct.getId()).getTransactions().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId()));
				// System.out.println(isTransactionInProduct);
				// System.out.println(productRepository.findOne(savedProduct.getId()).getTransactions().stream()
				// .filter(transaction ->
				// transaction.getId().equals(savedTransaction.getId()))
				// .collect(Collectors.toList()).get(0).getCurrentStatus());

		// then
		assertNull(customerService.findCustomerById(savedCustomer.getId()));
		assertNull(transactionService.findTransactionById(savedTransaction.getId()));
		assertNotNull(productService.findProductById(savedProduct.getId()));
		assertNotNull(productService.findProductById(savedProduct2.getId()));
		assertFalse(isTransactionInProduct);

	}

}
