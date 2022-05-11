package com.finalproyect.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "SHIPPINGADDRESS")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SHIPPINGADDRESSID")
    private Long shippingAddressId;

    @JoinColumn(name = "city_id")
    @ManyToOne
    private City city;

    @Column(name="POSTALADDRESS")
    private Integer postalAddress;
}
