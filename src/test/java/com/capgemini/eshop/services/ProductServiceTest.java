package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.types.ProductTO;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class ProductServiceTest {

	@Autowired
	ProductService productService;

	@Autowired
	DataCreator dataCreator;

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

}
