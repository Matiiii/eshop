package com.capgemini.eshop.dao.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capgemini.eshop.dao.ProductDaoCustom;
import com.capgemini.eshop.domain.QCustomerEntity;
import com.capgemini.eshop.domain.QProductEntity;
import com.capgemini.eshop.domain.QTransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ProductDaoImpl implements ProductDaoCustom {

	@PersistenceContext
	EntityManager entityManager;

	JPAQueryFactory qf;
	QCustomerEntity qcustomer;
	QTransactionEntity qtransaction;
	QProductEntity qproduct;

	@PostConstruct
	private void init() {

		qf = new JPAQueryFactory(entityManager);
		qcustomer = QCustomerEntity.customerEntity;
		qtransaction = QTransactionEntity.transactionEntity;
		qproduct = QProductEntity.productEntity;

	}
}
