package com.finalproyect.model.dtos;

import com.finalproyect.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {

    private String name;

    private String productCode;

    private Integer quantity;


    public ProductDto(Product product){
        this.name=product.getName();
        this.productCode=product.getProductCode();
        this.quantity=product.getStock();
    }
}
