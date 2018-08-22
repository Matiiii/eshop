package com.capgemini.eshop.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.embeddable.Address;
import com.capgemini.eshop.domain.embeddable.PersonalDetail;
import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.types.CustomerTO;

@Component
public class DataCreator {

	@Autowired
	CustomerService customerService;

	public CustomerTO saveNewCustomerKrzysztof() {

		Set<Long> orders = new HashSet<>();
		PersonalDetail personalDetail = new PersonalDetail();
		personalDetail.setName("Krzysztof");
		personalDetail.setSurname("Jarzyna");
		personalDetail.setPesel(90080132454545L);
		personalDetail.setBirthday(new Date(90, 8, 1));
		personalDetail.setEmail("orzeszek@gmail.com");

		Address address = new Address("Lazurowa", "Poznań", "60-655", "2b", "Poland");

		CustomerTO newCustomer = CustomerTO.builder().address(address).personalDetail(personalDetail).orders(orders)
				.build();

		return customerService.saveNewCustomer(newCustomer);

	}

}
