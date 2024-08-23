package com.ifif.service;

import com.ifif.dto.PaymentCallbackRequest;
import com.ifif.dto.RequestPayDto;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;


public interface PaymentService {
    //결제 요청 데이터 조회
    RequestPayDto findRequestDto(Long id);
    //결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);
}
