package com.finalproyect.services;

import com.finalproyect.entities.Product;
import com.finalproyect.entities.ProductForPrucharase;
import com.finalproyect.exceptions.LackOfStockException;
import com.finalproyect.exceptions.ProductNotFoundException;
import com.finalproyect.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public Product checkQuantityAndLoad(String productCode, Integer quantity) {
        Product product = this.productRepository.findByProductCode(productCode);
        if (product == null) throw new ProductNotFoundException("no existe un producto con ese ID product");
        if (product.getStock() >= quantity) return product;
        throw new LackOfStockException("El stock actual del producto" + product.getStock() + "por lo que no se puede reservar" + quantity + "unidades");

    }

    public ProductForPrucharase retrieveProduct(String productCode, Integer quantity) {
        Product product = checkQuantityAndLoad(productCode, quantity);
        Integer finalQuantity = product.getStock() - quantity;
        product.setStock(finalQuantity);
        this.productRepository.save(product);
        ProductForPrucharase productForShoppingCart = new ProductForPrucharase();
        productForShoppingCart.setProductCode(productCode);
        productForShoppingCart.setQuantity(quantity);
        productForShoppingCart.setName(product.getName());
        return productForShoppingCart;

    }
}
