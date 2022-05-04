package com.finalproyect.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shippingAddressId;

    @JoinColumn(name = "city_id")
    @ManyToOne
    private City city;

    @Column
    private Integer postalAddress;
}
