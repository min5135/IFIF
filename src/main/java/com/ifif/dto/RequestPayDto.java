package com.ifif.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestPayDto {

    private String itemNm;
    private String buyerName;
    private int orderPrice;
    private String buyerEmail;
    private String buyerAddress;
    private String orderUid;
    private String buyerTel;


    @Builder
    public RequestPayDto(String itemNm, String buyerName, int orderPrice, String buyerEmail, String buyerAddress, String orderUid,String buyerTel) {

        this.itemNm = itemNm;
        this.buyerName = buyerName;
        this.orderPrice = orderPrice;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
        this.orderUid = orderUid;
        this.buyerTel = buyerTel;
    }

}
