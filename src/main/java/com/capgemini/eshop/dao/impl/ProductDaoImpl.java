package com.capgemini.eshop.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.capgemini.eshop.dao.ProductDaoCustom;
import com.capgemini.eshop.domain.ProductEntity;
import com.capgemini.eshop.domain.QCustomerEntity;
import com.capgemini.eshop.domain.QProductEntity;
import com.capgemini.eshop.domain.QTransactionEntity;
import com.capgemini.eshop.enums.Status;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
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

	@Override
	public List<Object[]> findProductsWitsStatusInProgress() {

		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

		List<Tuple> tuple = qf.select(qproduct.name, qproduct.id.count().as(count)).from(qtransaction)
				.innerJoin(qtransaction.products, qproduct).where(qtransaction.currentStatus.eq(Status.IN_PROGRESS))
				.groupBy(qproduct.id).fetch();

		return tuple.stream().map(t -> t.toArray()).collect(Collectors.toList());
	}

	@Override
	public List<ProductEntity> findTopProducts(Integer number) {

		NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

		List<Tuple> tuple = qf.select(qproduct, qproduct.id.count().as(count)).from(qtransaction)
				.innerJoin(qtransaction.products, qproduct).groupBy(qproduct.id).orderBy(count.desc()).limit(number)
				.fetch();

		return tuple.stream().map(t -> (ProductEntity) t.toArray()[0]).collect(Collectors.toList());
	}
}
