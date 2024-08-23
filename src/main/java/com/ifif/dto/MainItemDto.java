package com.ifif.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ifif.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private Integer price;
    @QueryProjection //Querydsl 결과 조회 시 MainItemDto 객체로 바로 오도록  활용
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
    public static MainItemDto of(Item item) {
        String imgUrl = (item.getItemImgList() != null && !item.getItemImgList().isEmpty())
                ? item.getItemImgList().get(0).getImgUrl()
                : null; // 이미지가 없으면 null
        return new MainItemDto(item.getId(), item.getItemNm(), item.getItemDetail(), imgUrl, item.getPrice());
    }
}
