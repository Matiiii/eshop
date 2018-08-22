package com.capgemini.eshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.ProductEntity;

@Repository
public interface ProductDao extends CrudRepository<ProductEntity, Long> {

}
