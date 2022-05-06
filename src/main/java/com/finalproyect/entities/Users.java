package com.finalproyect.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERID")
    private Long userId;

    @Column(name = "USERNAME")
    private String name;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKOUT")
    private Checkout checkout;

    @Column(name = "KEYCLOAKID", unique = true)
    private String keycloakId;
}
