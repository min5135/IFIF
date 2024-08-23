package com.ifif.entity;

import com.ifif.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @Column(name = "order_price")
    private int orderPrice;
    private  String itemNm;
    private String orderUid; //주문번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
                orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();




    //private LocalDateTime regTime;

    //private LocalDateTime updateTime;

    //주문서 주문아이템 리스트에 주문 아이템 추가
    //주문 아이템에 주문서 추가
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    // 주문서 생성
    // 현재 로그인된 멤버 주문서에 추가
    // 주문아이템 리스트를 반복문을 통해서 주문서에 추가
    // 상태는 주문으로 세팅
    // 주문 시간은 현재시간으로 세팅
    // 주문서 리턴

    public static Order createOrder(Member member, List<OrderItem> orderItemList, Payment payment){
        Order order = new Order();
        order.setMember(member);
        order.setOrderUid(UUID.randomUUID().toString());

        order.setPayment(payment);
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    // 주문서에 있는 주문 아이템 리스트를 반복
    // 주문 아이템마다 총 가격을 tatalPrice에 추가
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
    @Builder
    public Order(int orderPrice, String itemNm, Long id, Member member, Payment payment,String orderUid){
        this.orderPrice = orderPrice;
        this.itemNm = itemNm;
        this.id = id;
        this.member = member;
        this.payment = payment;
        this.orderUid = UUID.randomUUID().toString();

    }
}
