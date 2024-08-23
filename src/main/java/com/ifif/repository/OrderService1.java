package com.ifif.repository;

import com.ifif.entity.Member;
import com.ifif.entity.Order;

public interface OrderService1 {
    Order autoOrder(Member member); //자동 주문
}
