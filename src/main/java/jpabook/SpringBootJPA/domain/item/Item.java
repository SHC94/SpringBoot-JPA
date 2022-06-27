package jpabook.SpringBootJPA.domain.item;

import jpabook.SpringBootJPA.domain.Category;
import jpabook.SpringBootJPA.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속 전략..
@DiscriminatorColumn(name="dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**비즈니스 로직*/
    /**재고 증가*/
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }//end addStock(0

    /**재고 감소*/
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }//end removeStock()
}
