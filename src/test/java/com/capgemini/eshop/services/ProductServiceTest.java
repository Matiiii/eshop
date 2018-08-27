package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
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
public class ProductServiceTest {

	@Autowired
	ProductService productService;

	@Autowired
	DataCreator dataCreator;

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService transactionService;

	@Transactional
	@Test
	public void shouldGetProductById() {

		// given

		ProductTO savedProduct = dataCreator.saveNewProductMajty();

		// when

		ProductTO selectedProduct = productService.findProductById(savedProduct.getId());

		// then
		assertNotNull(selectedProduct);
		assertEquals(savedProduct.getId(), selectedProduct.getId());
		assertEquals("Majty", selectedProduct.getName());

	}

	@Transactional
	@Test
	public void shouldUpdateProduct() {

		// given

		ProductTO savedProduct = dataCreator.saveNewProductMajty();
		savedProduct.setPrice(32.2);
		savedProduct.setRetailMargin(50.0);
		// when

		ProductTO updatedProduct = productService.updateProduct(savedProduct);

		// then
		assertNotNull(updatedProduct);
		assertEquals(savedProduct.getId(), updatedProduct.getId());
		assertEquals(new Double(50.0), updatedProduct.getRetailMargin());

	}

	@Transactional
	@Test
	public void shouldRemoveProduct() {

		// given

		ProductTO savedProduct = dataCreator.saveNewProductMajty();

		// when

		productService.removeProduct(savedProduct.getId());

		// then
		assertNull(productService.findProductById(savedProduct.getId()));

	}

	@Transactional
	@Test(expected = RuntimeException.class)
	public void shouldNotRemoveProductAndThrowException() {

		// given

		// when

		productService.removeProduct(1000000L);

	}

	@Transactional
	@Test
	public void shouldProductsWithStatusInProgress() {

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		CustomerTO savedCustomer2 = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct3 = dataCreator.saveNewProductToster();
		ProductTO savedProduct4 = dataCreator.saveNewProductToster();
		ProductTO savedProduct5 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct6 = dataCreator.saveNewProductMajty();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		List<Long> productList2 = new ArrayList<>();
		productList2.add(savedProduct3.getId());
		productList2.add(savedProduct2.getId());
		productList2.add(savedProduct2.getId());

		List<Long> productList3 = new ArrayList<>();
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());

		List<Long> productList4 = new ArrayList<>();
		productList4.add(savedProduct4.getId());
		productList4.add(savedProduct4.getId());
		productList4.add(savedProduct4.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);
		savedTransaction.setCurrentStatus(Status.IN_PROGRESS);
		transactionService.updateTransaction(savedTransaction);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList3);
		savedTransaction3.setCurrentStatus(Status.IN_PROGRESS);
		transactionService.updateTransaction(savedTransaction3);

		TransactionTO savedTransaction4 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList4);

		List<Object[]> resultList = productService.getProductsWithStatusInProgress();

		assertEquals(3, resultList.size());
		assertEquals(true, resultList.stream()
				.anyMatch(obj -> obj[0].equals(savedProduct4.getName()) && obj[1].toString().equals("3")));
		assertEquals(true, resultList.stream()
				.anyMatch(obj -> obj[0].equals(savedProduct.getName()) && obj[1].toString().equals("2")));
		assertEquals(true, resultList.stream()
				.anyMatch(obj -> obj[0].equals(savedProduct2.getName()) && obj[1].toString().equals("2")));

	}

	@Transactional
	@Test
	public void shouldGetTopProducts() {

		CustomerTO savedCustomer = dataCreator.saveNewCustomerJanusz();
		CustomerTO savedCustomer2 = dataCreator.saveNewCustomerJanusz();

		ProductTO savedProduct = dataCreator.saveNewProductMlotek();
		ProductTO savedProduct2 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct3 = dataCreator.saveNewProductToster();
		ProductTO savedProduct4 = dataCreator.saveNewProductToster();
		ProductTO savedProduct5 = dataCreator.saveNewProductMajty();
		ProductTO savedProduct6 = dataCreator.saveNewProductMajty();

		List<Long> productList = new ArrayList<>();
		productList.add(savedProduct.getId());
		productList.add(savedProduct.getId());
		productList.add(savedProduct2.getId());
		productList.add(savedProduct2.getId());

		List<Long> productList2 = new ArrayList<>();
		productList2.add(savedProduct3.getId());
		productList2.add(savedProduct2.getId());
		productList2.add(savedProduct2.getId());

		List<Long> productList3 = new ArrayList<>();
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());
		productList3.add(savedProduct4.getId());

		List<Long> productList4 = new ArrayList<>();
		productList4.add(savedProduct4.getId());
		productList4.add(savedProduct4.getId());
		productList4.add(savedProduct4.getId());

		TransactionTO savedTransaction = dataCreator.seveNewProductsOrderForCustomer(savedCustomer.getId(),
				productList);

		TransactionTO savedTransaction2 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList2);

		TransactionTO savedTransaction3 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList3);

		TransactionTO savedTransaction4 = dataCreator.seveNewProductsOrderForCustomer(savedCustomer2.getId(),
				productList4);

		List<ProductTO> resultList = productService.getTopProduct(4);

		assertEquals(4, resultList.size());
		assertEquals(savedProduct4.getId(), resultList.get(0).getId());
		assertEquals(savedProduct2.getId(), resultList.get(1).getId());
		assertEquals(savedProduct.getId(), resultList.get(2).getId());
		assertEquals(savedProduct3.getId(), resultList.get(3).getId());

	}

	@Transactional
	@Test(expected = OptimisticLockingFailureException.class)
	public void shouldNotUpdateProduct() {

		// given

		ProductTO savedProduct = dataCreator.saveNewProductMajty();
		savedProduct.setPrice(32.2);
		savedProduct.setRetailMargin(50.0);
		// when

		ProductTO updatedProduct = productService.updateProduct(savedProduct);

		savedProduct.setVersion(100);
		savedProduct.setPrice(50.2);
		savedProduct.setRetailMargin(100.0);
		productService.updateProduct(savedProduct);

	}

	@Test(expected = OptimisticLockingFailureException.class)
	public void shouldNotUpdateProduct2() {

		// given

		ProductTO savedProduct = dataCreator.saveNewProductMajty();
		savedProduct.setPrice(32.2);
		savedProduct.setRetailMargin(50.0);
		// when

		ProductTO updatedProduct = productService.updateProduct(savedProduct);

		savedProduct.setPrice(50.2);
		savedProduct.setRetailMargin(100.0);
		productService.updateProduct(savedProduct);

	}

}
