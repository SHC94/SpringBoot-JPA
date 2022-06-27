package jpabook.SpringBootJPA.service;

import jpabook.SpringBootJPA.domain.item.Item;
import jpabook.SpringBootJPA.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**상품 저장*/
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }//end saveItem()

    /**상품 조회 (다건)*/
    public List<Item> findItems() {
        return itemRepository.findAll();
    }//end findItems()

    /**상품 조회 (단건)*/
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }//end findOne()

}//end class()
