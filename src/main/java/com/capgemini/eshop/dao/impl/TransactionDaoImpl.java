package com.capgemini.eshop.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capgemini.eshop.dao.TransactionDaoCustom;
import com.capgemini.eshop.domain.QCustomerEntity;
import com.capgemini.eshop.domain.QProductEntity;
import com.capgemini.eshop.domain.QTransactionEntity;
import com.capgemini.eshop.domain.TransactionEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class TransactionDaoImpl implements TransactionDaoCustom {

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
	public List<TransactionEntity> findTransactionByCustomerId(Long customerId) {
		return qf.selectFrom(qtransaction).join(qtransaction.customer, qcustomer)
				.where(qtransaction.customer.id.eq(customerId)).fetch();

	}

	@Override
	public List<TransactionEntity> findTransactionByCritria(TransactionSearchCriteria criteria) {
		criteria.checkCriteria();
		BooleanBuilder serchQuery = new BooleanBuilder();

		if (criteria.getCustomerName() != null) {
			serchQuery.and(qtransaction.customer.personalDetail.name.eq(criteria.getCustomerName()));
		}
		if (criteria.getProductId() != null) {
			serchQuery.and(qproduct.id.eq(criteria.getProductId()));

		}
		if (criteria.getCostMin() != null && criteria.getCostMin() != null) {
			serchQuery.and(qtransaction.sumCost.between(criteria.getCostMin(), criteria.getCostMin()));
		}
		if ((criteria.getTransactionStart() != null) && (criteria.getTransactionStop() != null)) {

			serchQuery.and(qtransaction.date.between(criteria.getTransactionStart(), criteria.getTransactionStop()));
		}

		return qf.selectFrom(qtransaction).join(qtransaction.products, qproduct).where(serchQuery).fetch();

	}

}
