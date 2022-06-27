package jpabook.SpringBootJPA.repository;

import jpabook.SpringBootJPA.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    /**상품 저장*/
    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }//end if()
    }//end save()

    /**상품 조회 (단건)*/
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }//end findOne()

    /**상품 조회 (다건)*/
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }//end findAll
}
