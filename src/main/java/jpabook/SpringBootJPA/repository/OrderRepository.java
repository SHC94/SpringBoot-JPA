package jpabook.SpringBootJPA.repository;

import jpabook.SpringBootJPA.domain.Member;
import jpabook.SpringBootJPA.domain.Order;
import jpabook.SpringBootJPA.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    /**주문생성*/
    public void save(Order order) {
        em.persist(order);
    }

    /**주문조회 (단건)*/
    public Order findOne(Long id) {
        return em.find(Order.class , id);
    }

    /**주문조회 (다건) 동적 쿼리*/
    public List<Order> findAll(OrderSearch orderSearch) {
//        List<Order> result = em.createQuery("select o from Order o join o.member m" +
//                        " where 1=1" +
//                        " and o.status = :status" +
//                        " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(0)
//                .setMaxResults(1000)
//                .getResultList();
//        return result;
        /**==================================================================================*/
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 조건이 존재하는 경우
        if(orderSearch.getOrderStatus() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }//end if()
            jpql += " o.status = :status";
        }//end if()

        //회원 이름 조건이 존재하는 경우
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }//end if()
            jpql += " m.name like :name";
        }//end if()

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
        /**==================================================================================*/
    }//end findAll()

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건

        return query.getResultList();
    }//end findAllByCriteria()

}
