package com.capgemini.eshop.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capgemini.eshop.dao.TransactionDaoCustom;
import com.capgemini.eshop.domain.TransactionEntity;
import com.capgemini.eshop.domain.qdsl.QCustomerEntity;
import com.capgemini.eshop.domain.qdsl.QProductEntity;
import com.capgemini.eshop.domain.qdsl.QTransactionEntity;
import com.capgemini.eshop.enums.Status;
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
	public List<TransactionEntity> findTransactionsByCustomerId(Long customerId) {
		return qf.selectFrom(qtransaction).join(qtransaction.customer, qcustomer)
				.where(qtransaction.customer.id.eq(customerId)).fetch();

	}

	@Override
	public List<TransactionEntity> findTransactionsByCritria(TransactionSearchCriteria criteria) {
		criteria.checkCriteria();
		BooleanBuilder serachQuery = new BooleanBuilder();

		if (criteria.getCustomerName() != null) {
			serachQuery.and(qtransaction.customer.personalDetail.name.eq(criteria.getCustomerName()));
		}
		if (criteria.getProductId() != null) {
			serachQuery.and(qproduct.id.eq(criteria.getProductId()));

		}
		if (criteria.getCostMin() != null && criteria.getCostMax() != null) {
			serachQuery.and(qtransaction.sumCost.between(criteria.getCostMin(), criteria.getCostMax()));
		}
		if ((criteria.getTransactionStart() != null) && (criteria.getTransactionStop() != null)) {

			serachQuery.and(qtransaction.date.between(criteria.getTransactionStart(), criteria.getTransactionStop()));
		}

		return qf.selectFrom(qtransaction).innerJoin(qtransaction.products, qproduct).where(serachQuery).fetch();

	}

	@Override
	public Double sumOfAllTransactionsByCustomerId(Long customerId) {

		return qf.select(qtransaction.sumCost.sum()).from(qtransaction).where(qtransaction.customer.id.eq(customerId))
				.fetchOne();
	}

	@Override
	public Double sumOfAllTransactionsByCustomerIdAndStatus(Long customerId, Status status) {

		return qf.select(qtransaction.sumCost.sum()).from(qtransaction).where(qtransaction.customer.id.eq(customerId))
				.where(qtransaction.currentStatus.eq(status)).fetchOne();
	}

	@Override
	public Double sumOfAllTransactionsByStatus(Status status) {

		return qf.select(qtransaction.sumCost.sum()).from(qtransaction).where(qtransaction.currentStatus.eq(status))
				.fetchOne();
	}

	@Override
	public Double calculateProfitInPeriod(Date from, Date to) {
		return qf.select((qproduct.price.multiply(qproduct.retailMargin).divide(100)).sum()).from(qtransaction)
				.innerJoin(qtransaction.products, qproduct).where(qtransaction.date.between(from, to))
				.where(qtransaction.currentStatus.eq(Status.COMPLETED)).fetchOne();

	}

}