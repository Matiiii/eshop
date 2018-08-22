package com.capgemini.eshop.service;

import com.capgemini.eshop.types.ProductTO;

public interface ProductService {

	ProductTO saveNewProduct(ProductTO productToSave);

	ProductTO findProductById(Long id);

}
