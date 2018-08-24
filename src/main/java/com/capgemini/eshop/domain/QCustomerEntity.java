package com.capgemini.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerEntity is a Querydsl query type for CustomerEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerEntity extends EntityPathBase<CustomerEntity> {

    private static final long serialVersionUID = -399810684L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerEntity customerEntity = new QCustomerEntity("customerEntity");

    public final com.capgemini.eshop.domain.embeddable.QAbstractEntity _super = new com.capgemini.eshop.domain.embeddable.QAbstractEntity(this);

    public final com.capgemini.eshop.domain.embeddable.QAddress address;

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<TransactionEntity, QTransactionEntity> orders = this.<TransactionEntity, QTransactionEntity>createSet("orders", TransactionEntity.class, QTransactionEntity.class, PathInits.DIRECT2);

    public final com.capgemini.eshop.domain.embeddable.QPersonalDetail personalDetail;

    //inherited
    public final DateTimePath<java.util.Date> updated = _super.updated;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCustomerEntity(String variable) {
        this(CustomerEntity.class, forVariable(variable), INITS);
    }

    public QCustomerEntity(Path<? extends CustomerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerEntity(PathMetadata metadata, PathInits inits) {
        this(CustomerEntity.class, metadata, inits);
    }

    public QCustomerEntity(Class<? extends CustomerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.capgemini.eshop.domain.embeddable.QAddress(forProperty("address")) : null;
        this.personalDetail = inits.isInitialized("personalDetail") ? new com.capgemini.eshop.domain.embeddable.QPersonalDetail(forProperty("personalDetail")) : null;
    }

}

