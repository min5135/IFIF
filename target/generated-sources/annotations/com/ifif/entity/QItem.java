package com.ifif.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1693383113L;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<CommentEntity, QCommentEntity> commentEntityList = this.<CommentEntity, QCommentEntity>createList("commentEntityList", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath dataSource = createString("dataSource");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemDetail = createString("itemDetail");

    public final ListPath<ItemImg, QItemImg> itemImgList = this.<ItemImg, QItemImg>createList("itemImgList", ItemImg.class, QItemImg.class, PathInits.DIRECT2);

    public final StringPath itemNm = createString("itemNm");

    public final EnumPath<com.ifif.constant.ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", com.ifif.constant.ItemSellStatus.class);

    public final ListPath<Member, QMember> member = this.<Member, QMember>createList("member", Member.class, QMember.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

