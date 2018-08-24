package com.capgemini.eshop.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.embeddable.Address;
import com.capgemini.eshop.domain.embeddable.PersonalDetail;
import com.capgemini.eshop.enums.Status;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.service.TransactionService;
import com.capgemini.eshop.types.CustomerTO;
import com.capgemini.eshop.types.ProductTO;
import com.capgemini.eshop.types.TransactionTO;

@Component
public class DataCreator {

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService orderService;

	@Autowired
	ProductService productService;

	public CustomerTO saveNewCustomerJanusz() {

		Set<Long> orders = new HashSet<>();
		PersonalDetail personalDetail = new PersonalDetail();
		personalDetail.setName("Janusz");
		personalDetail.setSurname("Jarzyna");
		personalDetail.setPesel(90080132454545L);
		personalDetail.setBirthday(new Date(90, 8, 1));
		personalDetail.setEmail("orzeszek@gmail.com");

		Address address = new Address("Warzywna", "Poznań", "60-655", "2b", "Poland");

		CustomerTO newCustomer = CustomerTO.builder().address(address).personalDetail(personalDetail).orders(orders)
				.build();

		return customerService.saveNewCustomer(newCustomer);

	}

	public CustomerTO saveNewCustomerKaryna() {

		Set<Long> orders = new HashSet<>();
		PersonalDetail personalDetail = new PersonalDetail();
		personalDetail.setName("Katarzyna");
		personalDetail.setSurname("Karyna");
		personalDetail.setPesel(90080132454545L);
		personalDetail.setBirthday(new Date(90, 8, 1));
		personalDetail.setEmail("orzeszek@gmail.com");

		Address address = new Address("Mostowa", "Poznań", "60-655", "2b", "Poland");

		CustomerTO newCustomer = CustomerTO.builder().address(address).personalDetail(personalDetail).orders(orders)
				.build();

		return customerService.saveNewCustomer(newCustomer);

	}

	public TransactionTO seveNewProductsOrderForCustomer(Long customerId, List<Long> productsId) {

		TransactionTO orderTosave = TransactionTO.builder().date(new Date()).currentStatus(Status.WAITING_FOR_PAYMENT)
				.products(productsId).customer(customerId).build();

		return orderService.saveNewTransaction(orderTosave);
	}

	public ProductTO saveNewProductMajty() {

		ProductTO productToSave = ProductTO.builder().name("Majty").price(22.5).retailMargin(10.0).weight(50).build();

		return productService.saveNewProduct(productToSave);

	}

	public ProductTO saveNewProductMlotek() {

		ProductTO productToSave = ProductTO.builder().name("Młotek").price(500.0).retailMargin(0.0).weight(500).build();

		return productService.saveNewProduct(productToSave);
	}

	public ProductTO saveNewProductTv() {

		ProductTO productToSave = ProductTO.builder().name("Telewizor").price(8000.0).retailMargin(25.0).weight(500)
				.build();

		return productService.saveNewProduct(productToSave);

	}

}
