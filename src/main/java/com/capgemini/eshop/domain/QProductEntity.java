package com.capgemini.eshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductEntity is a Querydsl query type for ProductEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductEntity extends EntityPathBase<ProductEntity> {

    private static final long serialVersionUID = -1780076753L;

    public static final QProductEntity productEntity = new QProductEntity("productEntity");

    public final com.capgemini.eshop.domain.embeddable.QAbstractEntity _super = new com.capgemini.eshop.domain.embeddable.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final NumberPath<Double> retailMargin = createNumber("retailMargin", Double.class);

    public final SetPath<TransactionEntity, QTransactionEntity> transactions = this.<TransactionEntity, QTransactionEntity>createSet("transactions", TransactionEntity.class, QTransactionEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> updated = _super.updated;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QProductEntity(String variable) {
        super(ProductEntity.class, forVariable(variable));
    }

    public QProductEntity(Path<? extends ProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductEntity(PathMetadata metadata) {
        super(ProductEntity.class, metadata);
    }

}

