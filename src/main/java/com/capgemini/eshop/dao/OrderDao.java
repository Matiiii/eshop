package com.capgemini.eshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.OrderEntity;

@Repository
public interface OrderDao extends CrudRepository<OrderEntity, Long> {

}
