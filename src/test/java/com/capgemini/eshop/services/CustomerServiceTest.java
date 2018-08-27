package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.eshop.dao.ProductDao;
import com.capgemini.eshop.enums.Status;
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
	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionsWhenRemoveInvalidCustomer() {

		// given

		customerService.removeCustomer(10000L);

		// then

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

		// then
		assertNull(customerService.findCustomerById(savedCustomer.getId()));
		assertNull(transactionService.findTransactionById(savedTransaction.getId()));
		assertNotNull(productService.findProductById(savedProduct.getId()));
		assertNotNull(productService.findProductById(savedProduct2.getId()));
		assertFalse(isTransactionInProduct);

	}

	@Transactional
	@Test
	public void shouldGet3CustomerWhoSpendMost() {

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();// 1044
																		// -4
		CustomerTO savedCustomer2 = dataCreator.saveNewCustomerKaryna();// 209
																		// -19
		CustomerTO savedCustomer3 = dataCreator.saveNewCustomerRychu();// 1990
																		// -90
		CustomerTO savedCustomer4 = dataCreator.saveNewCustomerWiola();// 0
		CustomerTO savedCustomer5 = dataCreator.saveNewCustomerSebix();// 165
																		// -15

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();// 500 -0
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();// 22 -2
		ProductTO savedProduct3 = dataCreator.saveNewProductToster();// 165 -15
		ProductTO savedProduct4 = dataCreator.saveNewProductToster();// 165

		List<Long> productList = new ArrayList<>();// 1044 -4
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		List<Long> productList2 = new ArrayList<>();// 209 -19
		productList2.add(savedProduct3.getId());
		productList2.add(savedProduct2.getId());
		productList2.add(savedProduct2.getId());

		List<Long> productList3 = new ArrayList<>();// 995 -45
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct.getId());

		List<Long> productList4 = new ArrayList<>();// 165 -15
		productList4.add(savedProduct4.getId());

		List<Long> productList5 = new ArrayList<>();// 1155 -105
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());
		productList5.add(savedProduct4.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList2);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer3.getId(),
				productList3);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction3);

		TransactionTO savedTransaction4 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer3.getId(),
				productList3);
		savedTransaction4.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction4);

		TransactionTO savedTransaction5 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer4.getId(),
				productList3);
		savedTransaction5.setCurrentStatus(Status.IN_PROGRESS);
		transactionService.updateTransaction(savedTransaction5);

		TransactionTO savedTransaction6 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer5.getId(),
				productList5);
		savedTransaction6.setCurrentStatus(Status.COMPLETED);
		savedTransaction6.setDate(new Date(98, 12, 12));
		transactionService.updateTransaction(savedTransaction6);

		TransactionTO savedTransaction7 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer5.getId(),
				productList4);
		savedTransaction7.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction7);

		List<CustomerTO> resultList = customerService.find3CustomersWhoSpentMostInPeriod(new Date(99, 12, 12),
				new Date(120, 12, 12));

		System.out.println(resultList.get(0).toString());
		System.out.println(resultList.get(1).toString());
		System.out.println(resultList.get(2).toString());
		System.out.println(transactionService.calculateProfitInPeriod(new Date(99, 12, 12), new Date(120, 12, 12)));

		assertEquals(3, resultList.size());
		assertEquals(savedCustomer3.getId(), resultList.get(0).getId());
		assertEquals(savedCustomer.getId(), resultList.get(1).getId());
		assertEquals(savedCustomer2.getId(), resultList.get(2).getId());

	}

}
