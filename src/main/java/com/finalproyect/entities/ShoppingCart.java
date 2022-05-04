package com.finalproyect.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany( cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private List<ProductForPrucharase> productsInShoppingCart;

    public ShoppingCart() {
        this.productsInShoppingCart=new ArrayList<>();
    }


}
