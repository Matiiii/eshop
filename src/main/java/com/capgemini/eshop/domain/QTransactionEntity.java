package com.capgemini.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;

/**
 * QTransactionEntity is a Querydsl query type for TransactionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionEntity extends EntityPathBase<TransactionEntity> {

	private static final long serialVersionUID = -869548194L;

	private static final PathInits INITS = PathInits.DIRECT2;

	public static final QTransactionEntity transactionEntity = new QTransactionEntity("transactionEntity");

	public final com.capgemini.eshop.domain.embeddable.QAbstractEntity _super = new com.capgemini.eshop.domain.embeddable.QAbstractEntity(
			this);

	// inherited
	public final DateTimePath<java.util.Date> created = _super.created;

	public final EnumPath<com.capgemini.eshop.enums.Status> currentStatus = createEnum("currentStatus",
			com.capgemini.eshop.enums.Status.class);

	public final QCustomerEntity customer;

	public final DatePath<java.util.Date> date = createDate("date", java.util.Date.class);

	public final NumberPath<Long> id = createNumber("id", Long.class);

	public final ListPath<ProductEntity, QProductEntity> products = this.<ProductEntity, QProductEntity> createList(
			"products", ProductEntity.class, QProductEntity.class, PathInits.DIRECT2);

	public final NumberPath<Double> sumCost = createNumber("sumCost", Double.class);

	// inherited
	public final DateTimePath<java.util.Date> updated = _super.updated;

	// inherited
	public final NumberPath<Integer> version = _super.version;

	public QTransactionEntity(String variable) {
		this(TransactionEntity.class, forVariable(variable), INITS);
	}

	public QTransactionEntity(Path<? extends TransactionEntity> path) {
		this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
	}

	public QTransactionEntity(PathMetadata metadata) {
		this(metadata, PathInits.getFor(metadata, INITS));
	}

	public QTransactionEntity(PathMetadata metadata, PathInits inits) {
		this(TransactionEntity.class, metadata, inits);
	}

	public QTransactionEntity(Class<? extends TransactionEntity> type, PathMetadata metadata, PathInits inits) {
		super(type, metadata, inits);
		this.customer = inits.isInitialized("customer")
				? new QCustomerEntity(forProperty("customer"), inits.get("customer")) : null;
	}

}
