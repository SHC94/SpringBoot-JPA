package jpabook.SpringBootJPA.service;

import jpabook.SpringBootJPA.domain.Delivery;
import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.domain.Order;
import jpabook.SpringBootJPA.domain.OrderItem;
import jpabook.SpringBootJPA.domain.item.Item;
import jpabook.SpringBootJPA.repository.ItemRepository;
import jpabook.SpringBootJPA.repository.MemberRepository;
import jpabook.SpringBootJPA.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @param memberId  회원 ID
     * @param itemId    상품 ID
     * @param count     주문 수량
     * @return
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member   = memberRepository.findOne(memberId);
        Item item       = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }//end order()

    /**
     * 주문 취소
     * @param orderId 주문 번호
     */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        /**도메인 모델 패턴 : 엔티티가 비즈니스 로직을 가지고 객체 지향의 특성을 활용하는 것*/
        /**트랜잭션 스크립트 패턴 : 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것*/
        //주문 취소
        order.cancel();
    }//end cancelOrder()

    /**
     * 주문 검색
     * @param orderSearch
     * @return
     */
//    public List<Order> findOrder(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }//end findOrder()
}
