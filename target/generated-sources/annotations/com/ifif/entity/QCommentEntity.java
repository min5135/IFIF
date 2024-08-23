package com.ifif.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = -64030594L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath commentContents = createString("commentContents");

    public final StringPath commentWriter = createString("commentWriter");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

