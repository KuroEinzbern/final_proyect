package com.finalproyect.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "ORDERTABLE")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ShoppingCart shoppingCart;

    @OneToOne
    private ShippingAddress shippingAddress;

    @Column
    private Double totalCost;

    @Column
    private UUID paymentId;

}
