package com.capgemini.eshop.service;

import java.util.List;

import com.capgemini.eshop.types.ProductTO;

public interface ProductService {

	ProductTO saveNewProduct(ProductTO productToSave);

	ProductTO findProductById(Long id);

	ProductTO updateProduct(ProductTO product);

	void removeProduct(Long id);

	List<ProductTO> getTopProduct(Integer limit);

	List<Object[]> getProductsWithStatusInProgress();

}
