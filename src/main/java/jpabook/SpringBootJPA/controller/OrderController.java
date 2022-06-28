package jpabook.SpringBootJPA.controller;

import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.domain.Order;
import jpabook.SpringBootJPA.domain.item.Item;
import jpabook.SpringBootJPA.repository.OrderSearch;
import jpabook.SpringBootJPA.service.ItemService;
import jpabook.SpringBootJPA.service.MemberService;
import jpabook.SpringBootJPA.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 상품 주문 페이지 이동
     * @param model
     * @return
     */
    @GetMapping(value = "/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    /**
     * 상품 주문
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    /**
     * 주문 내역 조회
     * @param orderSearch
     * @param model
     * @return
     */
    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
    /**
     * 주문 취소
     * @param orderId
     * @return
     */
    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}//end class()
