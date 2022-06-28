package jpabook.SpringBootJPA.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    컬렉션은 필드에서 초기화 하자.
//    컬렉션은 필드에서 바로 초기화 하는 것이 안전하다.
//    null 문제에서 안전하다.
//    하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다.
//    만약 getOrders() 처럼 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에
//    문제가 발생할 수 있다. 따라서 필드레벨에서 생성하는 것이 가장 안전하고, 코드도 간결하다
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //em.persist(orderItemA)
    //em.persist(orderItemB)
    //em.persist(orderItemC)
    //em.persist(order)

    //cascade = CascadeType.ALL 영속성 전파
    //em.persist(order)

    //@XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한다
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 : ORDER, CANCEL

    //연관관계 편의 메서드
    public void setMember(Member member){
//        Member member = new Member();
//        Order order = new Order();
//        member.getOrders().add(order);
//        order.setMember(member);

        this.member = member;
        member.getOrders().add(this);

//        order.setMember(member);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /*생성 메서드*/
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }//end for()

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }//end createOrder()

    /*비즈니스 로직*/
    /**주문 취소*/
    public void cancel() {
        //배송 상태 체크
        if(delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }//end if()

        //주문 상태 변경
        this.setStatus(OrderStatus.CANCEL);

        //재고 수량 원복
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }//end for()

    }//end cancel()

    /**전체 주문 가격 조회*/
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            //getTotalPrice : 주문 가격 * 주문 수량
            totalPrice += orderItem.getTotalPrice();
        }//end for()
        return totalPrice;
    }//end getTotalPrice()

    /**조회*/


}//end class()
