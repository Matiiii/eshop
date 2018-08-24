package com.capgemini.eshop.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.eshop.dao.ProductDao;
import com.capgemini.eshop.domain.ProductEntity;
import com.capgemini.eshop.mappers.ProductMapper;
import com.capgemini.eshop.service.ProductService;
import com.capgemini.eshop.types.ProductTO;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productRepository;

	@Autowired
	ProductMapper productMapper;

	@Override
	public ProductTO saveNewProduct(ProductTO productToSave) {

		ProductEntity savedProduct = productRepository.save(productMapper.map(productToSave));

		return productMapper.map(savedProduct);

	}

	@Override
	public ProductTO findProductById(Long id) {
		ProductEntity product = productRepository.findOne(id);

		return productMapper.map(product);
	}

	@Override
	public ProductTO updateProduct(ProductTO product) {
		ProductEntity productBeforeUpdate = productRepository.findOne(product.getId());
		ProductEntity productToUpdate = productMapper.map(product);
		productToUpdate.setTransactions(productBeforeUpdate.getTransactions());

		ProductEntity savedProduct = productRepository.save(productToUpdate);

		return productMapper.map(savedProduct);
	}

	@Override
	public void removeProduct(Long id) {
		if (productRepository.exists(id)) {

			productRepository.delete(id);
		} else {
			throw new RuntimeException("Product with id: " + id + " not exist!");
		}

	}

}
