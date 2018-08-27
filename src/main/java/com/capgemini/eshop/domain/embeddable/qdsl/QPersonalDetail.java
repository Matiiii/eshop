package com.capgemini.eshop.domain.embeddable.qdsl;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.capgemini.eshop.domain.embeddable.PersonalDetail;
import com.querydsl.core.types.Path;


/**
 * QPersonalDetail is a Querydsl query type for PersonalDetail
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPersonalDetail extends BeanPath<PersonalDetail> {

    private static final long serialVersionUID = -1444160137L;

    public static final QPersonalDetail personalDetail = new QPersonalDetail("personalDetail");

    public final DatePath<java.util.Date> birthday = createDate("birthday", java.util.Date.class);

    public final StringPath email = createString("email");

    public final StringPath name = createString("name");

    public final NumberPath<Long> pesel = createNumber("pesel", Long.class);

    public final NumberPath<Integer> phone = createNumber("phone", Integer.class);

    public final StringPath surname = createString("surname");

    public QPersonalDetail(String variable) {
        super(PersonalDetail.class, forVariable(variable));
    }

    public QPersonalDetail(Path<? extends PersonalDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonalDetail(PathMetadata metadata) {
        super(PersonalDetail.class, metadata);
    }

}

