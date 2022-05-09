package com.finalproyect.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COUNTRYID")
    private Long countryId;

    @Column(name = "COUNTRYNAME")
    private String name;

    @Column(name = "ISOCODE")
    private String isoCode;
}
