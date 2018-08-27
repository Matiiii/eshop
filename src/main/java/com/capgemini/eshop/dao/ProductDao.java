package com.capgemini.eshop.dao;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.ProductEntity;

@Transactional(value = TxType.REQUIRED)
@Repository
public interface ProductDao extends CrudRepository<ProductEntity, Long>, ProductDaoCustom {

}
