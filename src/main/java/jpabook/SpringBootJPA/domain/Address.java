package jpabook.SpringBootJPA.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Embeddable
@Getter //값 타입은.. 변경이 되면 안됨. 생성할 때만 값이 세팅이 되면 됨..  @Setter 제공 X
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
