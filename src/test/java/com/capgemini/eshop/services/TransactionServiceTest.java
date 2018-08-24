package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	@Test
	public void shouldSaveTransactionOver5000() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();

		List<Long> productListOver5000 = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
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
		for (int i = 0; i < 7; i++) {
			productListProductOver7000.add(savedProduct.getId());

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
	}

}
