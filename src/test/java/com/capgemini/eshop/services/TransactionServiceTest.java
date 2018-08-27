package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.eshop.dao.impl.TransactionSearchCriteria;
import com.capgemini.eshop.enums.Status;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.service.TransactionService;
import com.capgemini.eshop.types.CustomerTO;
import com.capgemini.eshop.types.ProductTO;
import com.capgemini.eshop.types.TransactionTO;

@RunWith(SpringRunner.class)
@SpringBootTest // (properties = "spring.profiles.active=mysql")
public class TransactionServiceTest {

	@Autowired
	DataCreator dataCreator;

	@Autowired
	CustomerService customerService;

	@Autowired
	ProductService productService;

	@Autowired
	TransactionService transactionService;

	@Transactional
	@Test
	public void shouldGetTransactionById() {

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

		TransactionTO selectedTransaction = transactionService.findTransactionById(savedTransaction.getId());

		// then
		ProductTO productAfter = productService.findProductById(savedProduct.getId());
		ProductTO product2After = productService.findProductById(savedProduct2.getId());

		assertNotNull(selectedTransaction);
		assertEquals(savedTransaction.getId(), selectedTransaction.getId());
		assertEquals(savedTransaction.getProducts(), selectedTransaction.getProducts());
		assertTrue(productAfter.getTransactions().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId())));
		assertTrue(product2After.getTransactions().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId())));

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotSaveTransactionProductNotExist() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMajty();
		ProductTO savedProduct2 = dataCreator.saveNewProductMlotek();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(10000L);
		productList.add(savedProduct2.getId());

		// when

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		// then

	}

	@Transactional
	@Test
	public void shouldRemoveTransactionById() {

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

		transactionService.removeTransaction(savedTransaction.getId());

		// then

		TransactionTO selectedTransaction = transactionService.findTransactionById(savedTransaction.getId());
		ProductTO productAfter = productService.findProductById(savedProduct.getId());
		ProductTO product2After = productService.findProductById(savedProduct.getId());
		CustomerTO customerAfter = customerService.findCustomerById(savedCustomer.getId());

		assertNull(selectedTransaction);
		assertFalse(productAfter.getTransactions().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId())));
		assertFalse(product2After.getTransactions().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId())));
		assertFalse(customerAfter.getOrders().stream()
				.anyMatch(transaction -> transaction.equals(savedTransaction.getId())));

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotSaveTransactionThrowException() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();

		List<Long> productList = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			productList.add(savedProduct.getId());
		}

		// when

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		// then

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotSaveTransactionThrowExceptionUserNotExist() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();

		List<Long> productList = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			productList.add(savedProduct.getId());
		}

		// when

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(100000L, productList);
		// then

	}

	@Transactional
	@Test
	public void shouldSaveTransactionOver5000() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();

		List<Long> productListOver5000 = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			productListOver5000.add(savedProduct.getId());
		}
		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setCurrentStatus(Status.COMPLETED);

		transactionService.updateTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction3);

		// when

		TransactionTO savedTransactionOver5000 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productListOver5000);
		// then

		TransactionTO selectedTransaction = transactionService.findTransactionById(savedTransactionOver5000.getId());

		assertNotNull(selectedTransaction);
		assertEquals(savedTransactionOver5000.getId(), selectedTransaction.getId());

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotSaveTransactionWithMoreLike5SameExpensiveProduct() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductTv();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();

		List<Long> productListProductOver7000 = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			productListProductOver7000.add(savedProduct.getId());
		}
		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setCurrentStatus(Status.COMPLETED);

		transactionService.updateTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction3);

		// when

		dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(), productListProductOver7000);
		// then
	}

	@Transactional
	@Test
	public void shouldReturnSetListOf3Transactions() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		savedTransaction.setCurrentStatus(Status.SENDED);

		transactionService.updateTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.updateTransaction(savedTransaction3);

		// when

		Set<TransactionTO> transactions = transactionService.findTransaktionsByCustomerId(savedCustomer.getId());

		// then

		assertNotNull(transactions);
		assertEquals(3, transactions.size());

	}

	@Transactional
	@Test
	public void shouldSerchByCriteria() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		CustomerTO savedCustomer2 = dataCreator.saveNewCustomerSebix();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct3 = dataCreator.saveNewProductTv();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		List<Long> productList2 = new ArrayList<>();
		productList2.add(savedProduct3.getId());

		List<Long> productList3 = new ArrayList<>();
		productList3.add(savedProduct.getId());
		productList3.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setDate(new Date(90, 12, 12));
		savedTransaction.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction3);

		TransactionTO savedTransaction4 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList2);
		savedTransaction4.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction4);

		TransactionTO savedTransaction5 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList3);
		savedTransaction5.setCurrentStatus(Status.IN_PROGRESS);
		transactionService.saveNewTransaction(savedTransaction5);

		// when
		TransactionSearchCriteria criteria = new TransactionSearchCriteria();
		criteria.setCustomerName("Janusz");
		criteria.setCostMin(0.0);
		criteria.setCostMax(6000.0);
		criteria.setTransactionStart(new Date(91, 1, 1));
		criteria.setTransactionStop(new Date(120, 1, 1));
		criteria.setProductId(savedProduct.getId());

		Set<TransactionTO> transactions = transactionService.findTransactionByCriteria(criteria);

		// then

		assertNotNull(transactions);
		assertEquals(2, transactions.size());

	}

	@Transactional
	@Test
	public void shouldReturnSumOfTransactions() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		// when

		Double sumCost = transactionService.getSumCostOfAllTransatctionsByCustomerID(savedCustomer.getId());

		// then

		assertNotNull(sumCost);
		assertEquals(new Double(3132.0), sumCost);

	}

	@Transactional
	@Test
	public void shouldGet128ProfitInPeriod() {

		// Given

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

		// When
		Double profit = transactionService.calculateProfitInPeriod(new Date(99, 12, 12), new Date(120, 12, 12));

		// Then
		assertEquals(new Double(128.0), profit);

	}

	@Transactional
	@Test
	public void shouldGetSumCostByCustomerIdAndStatus() {

		// Given

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

		// When
		Double cust4InProg = transactionService.sumOfAllTransactionByCustomerIdAndStatus(savedCustomer4.getId(),
				Status.IN_PROGRESS);
		Double cust5Com = transactionService.sumOfAllTransactionByCustomerIdAndStatus(savedCustomer5.getId(),
				Status.COMPLETED);
		Double cust1Wait = transactionService.sumOfAllTransactionByCustomerIdAndStatus(savedCustomer.getId(),
				Status.WAITING_FOR_PAYMENT);
		// Then

		assertEquals(new Double(995), cust4InProg);
		assertEquals(new Double(1320), cust5Com);
		assertEquals(new Double(1044), cust1Wait);

	}

	@Transactional
	@Test
	public void shouldGetSumCostByStatus() {

		// Given

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

		// When
		Double inPorgress = transactionService.sumOfAllTransactionsByStatus(Status.IN_PROGRESS);
		Double completed = transactionService.sumOfAllTransactionsByStatus(Status.COMPLETED);
		Double waiting = transactionService.sumOfAllTransactionsByStatus(Status.WAITING_FOR_PAYMENT);
		// Then

		assertEquals(new Double(995), inPorgress);
		assertEquals(new Double(3519), completed);
		assertEquals(new Double(1044), waiting);

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotRemoveTransactionAndThrowException() {

		// given

		// when

		transactionService.removeTransaction(1000000L);

	}

	@Transactional
	@Test
	public void shouldSerchThrowExceptionsWhenUseWrongCriteria() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		CustomerTO savedCustomer2 = dataCreator.saveNewCustomerSebix();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct3 = dataCreator.saveNewProductTv();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		List<Long> productList2 = new ArrayList<>();
		productList2.add(savedProduct3.getId());

		List<Long> productList3 = new ArrayList<>();
		productList3.add(savedProduct.getId());
		productList3.add(savedProduct2.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setDate(new Date(90, 12, 12));
		savedTransaction.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction2.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction3.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction3);

		TransactionTO savedTransaction4 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList2);
		savedTransaction4.setCurrentStatus(Status.COMPLETED);
		transactionService.saveNewTransaction(savedTransaction4);

		TransactionTO savedTransaction5 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList3);
		savedTransaction5.setCurrentStatus(Status.IN_PROGRESS);
		transactionService.saveNewTransaction(savedTransaction5);

		// when
		Integer numberOfexceptions = 0;

		Set<TransactionTO> transactions;

		TransactionSearchCriteria criteria = new TransactionSearchCriteria();
		criteria.setCustomerName("Janusz");
		criteria.setCostMin(0.0);
		criteria.setTransactionStart(new Date(91, 1, 1));
		criteria.setTransactionStop(new Date(120, 1, 1));
		criteria.setProductId(savedProduct.getId());

		try {
			transactionService.findTransactionByCriteria(criteria);
		} catch (Exception e1) {
			numberOfexceptions++;
			e1.printStackTrace();
		}

		TransactionSearchCriteria criteria2 = new TransactionSearchCriteria();
		criteria2.setCustomerName("Janusz");
		criteria2.setCostMax(600.0);

		try {
			transactionService.findTransactionByCriteria(criteria2);
		} catch (Exception e1) {
			numberOfexceptions++;

		}

		TransactionSearchCriteria criteria3 = new TransactionSearchCriteria();
		criteria3.setCustomerName("Janusz");
		criteria3.setCostMin(600.0);
		criteria3.setCostMax(400.0);

		try {
			transactionService.findTransactionByCriteria(criteria3);
		} catch (Exception e1) {
			numberOfexceptions++;

		}

		TransactionSearchCriteria criteria4 = new TransactionSearchCriteria();
		criteria4.setTransactionStop(new Date(120, 1, 1));

		try {
			transactionService.findTransactionByCriteria(criteria4);
		} catch (Exception e2) {
			numberOfexceptions++;

		}

		TransactionSearchCriteria criteria5 = new TransactionSearchCriteria();
		criteria5.setTransactionStart(new Date(91, 1, 1));

		try {
			transactionService.findTransactionByCriteria(criteria5);
		} catch (Exception e1) {
			numberOfexceptions++;

		}

		TransactionSearchCriteria criteria6 = new TransactionSearchCriteria();
		criteria6.setTransactionStart(new Date(120, 1, 1));
		criteria6.setTransactionStop(new Date(90, 1, 1));

		try {
			transactions = transactionService.findTransactionByCriteria(criteria6);
		} catch (Exception e) {
			numberOfexceptions++;

		}

		// then
		assertNotNull(numberOfexceptions);
		assertEquals(new Integer(6), numberOfexceptions);

	}

}
