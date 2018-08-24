package com.capgemini.eshop.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.eshop.dao.CustomerDaoCustom;
import com.capgemini.eshop.dao.ProductDao;
import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.domain.QCustomerEntity;
import com.capgemini.eshop.domain.QProductEntity;
import com.capgemini.eshop.domain.QTransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CustomerDaoImpl implements CustomerDaoCustom {

	@Autowired
	ProductDao productRepository;

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

	@Override
	public List<CustomerEntity> find3CustomerWhoSpendMostInYear() {
		return null;
		// return qf.selectFrom(qcustomer).join(qcustomer.orders,
		// qtransaction).join(qtransaction.products, qproduct).where(o)
	}

}
