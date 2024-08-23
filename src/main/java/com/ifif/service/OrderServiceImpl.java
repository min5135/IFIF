package com.ifif.service;

import com.ifif.constant.PaymentStatus;
import com.ifif.entity.Member;
import com.ifif.entity.Order;
import com.ifif.entity.Payment;
import com.ifif.repository.OrderRepository;
import com.ifif.repository.OrderService1;
import com.ifif.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService1 {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order autoOrder(Member member) {

        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(1000)
                .status(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);


        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .orderPrice(1000)
                .itemNm("1달러샵 상품")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();

        return orderRepository.save(order);
    }

}
