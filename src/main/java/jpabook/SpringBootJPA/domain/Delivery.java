package jpabook.SpringBootJPA.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //Default : ORDINAL - 1, 2, 3... 숫자로 들어감
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; //READY, COMP
}
