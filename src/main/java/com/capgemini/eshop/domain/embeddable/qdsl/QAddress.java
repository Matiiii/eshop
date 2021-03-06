package com.capgemini.eshop.domain.embeddable.qdsl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.capgemini.eshop.domain.embeddable.Address;
import com.querydsl.core.types.Path;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAddress extends BeanPath<Address> {

    private static final long serialVersionUID = 687221742L;

    public static final QAddress address = new QAddress("address");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final StringPath hauseNumber = createString("hauseNumber");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath street = createString("street");

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

