package com.capgemini.eshop.dao;

import java.util.List;

import com.capgemini.eshop.domain.ProductEntity;

public interface ProductDaoCustom {

	List<Object[]> findProductsWitsStatusInProgress();

	List<ProductEntity> findTopProducts(Integer number);

}
