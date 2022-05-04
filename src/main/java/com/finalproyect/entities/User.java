package com.finalproyect.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Checkout checkout;
}
