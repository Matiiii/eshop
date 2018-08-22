package com.capgemini.eshop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.eshop.domain.CustomerEntity;

@Repository
public interface CustomerDao extends CrudRepository<CustomerEntity, Long> {

}
