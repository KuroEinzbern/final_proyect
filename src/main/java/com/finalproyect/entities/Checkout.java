package com.finalproyect.entities;


import com.finalproyect.model.patterns.PaymentStrategiesEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHECKOUT")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long checkoutId;

    @OneToOne(cascade = CascadeType.MERGE,  fetch = FetchType.EAGER)
    private ShoppingCart shoppingCart;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private ShippingAddress shippingAddress;


    @Column
    private PaymentStrategiesEnum paymentStrategy;

}
