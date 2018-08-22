package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.eshop.service.CustomerService;
import com.capgemini.eshop.types.CustomerTO;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class CustomerServiceTest {

	@Autowired
	DataCreator dataCreator;

	@Autowired
	CustomerService customerService;

	@Transactional
	@Test
	public void shouldGetEmployeeById() {

		// given

		CustomerTO savedCustomer = dataCreator.saveNewCustomerKrzysztof();
		// when

		CustomerTO selectedCustomer = customerService.findEmployeeById(savedCustomer.getId());

		// then
		assertNotNull(selectedCustomer);
		assertEquals(savedCustomer.getId(), selectedCustomer.getId());
		assertEquals(savedCustomer.getPersonalDetail(), selectedCustomer.getPersonalDetail());

	}

}
