package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")    // Order 클래스의 delivery에 맵핑됨
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // [READY, COMP]

    // 양양
}
