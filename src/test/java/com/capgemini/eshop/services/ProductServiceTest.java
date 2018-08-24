package com.capgemini.eshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.types.ProductTO;

@RunWith(SpringRunner.class)
@SpringBootTest // (properties = "spring.profiles.active=mysql")
public class ProductServiceTest {

	@Autowired
	ProductService productService;

	@Autowired
	DataCreator dataCreator;

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

}
