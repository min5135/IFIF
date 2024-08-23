package com.ifif.entity;

import com.ifif.constant.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Payment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
    private PaymentStatus status;
    private String paymentUid; //결제고유번호

    @Builder
    public Payment(int price, PaymentStatus status){
        this.price = price;
        this.status = status;
    }
    public void changePaymentBySuccess(PaymentStatus status,String paymentUid){
        this.status = status;
        this.paymentUid = paymentUid;
    }


}
