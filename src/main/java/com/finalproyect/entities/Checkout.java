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
    @Column(name = "CHECKOUTID")
    private Long checkoutId;

    @OneToOne(cascade = CascadeType.MERGE,  fetch = FetchType.EAGER)
    @JoinColumn(name = "SHOPPINGCART")
    private ShoppingCart shoppingCart;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "SHIPPINGADDRESS")
    private ShippingAddress shippingAddress;


    @Column(name = "PAYMENTSTRATEGY")
    private PaymentStrategiesEnum paymentStrategy;

}
