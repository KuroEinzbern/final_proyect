package com.finalproyect.services;

import com.finalproyect.entities.ProductForPrucharase;
import com.finalproyect.entities.ShoppingCart;
import com.finalproyect.model.exceptions.ProductNotFoundException;
import com.finalproyect.repositories.ProductForPrucharaseRepository;
import com.finalproyect.repositories.ShopppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    ShopppingCartRepository shopppingCartRepository;

    @Autowired
    ProductForPrucharaseRepository productForPrucharaseRepository;



    public void addToShoppingCart(ProductForPrucharase product, ShoppingCart shoppingCart){
        if(!modifyQuantity(product, shoppingCart)){
            List<ProductForPrucharase> productList= shoppingCart.getProductsInShoppingCart();
            productList.add(product);
        }
        ShoppingCart que=this.shopppingCartRepository.save(shoppingCart);
    }


    private void removeFromTheShoppingCart(ProductForPrucharase product, ShoppingCart shoppingCart){
        List<ProductForPrucharase> productList= shoppingCart.getProductsInShoppingCart();
        if(!productList.remove(product)){
            throw new ProductNotFoundException("the product does not are in the shopping cart");
        }
    }

    private boolean modifyQuantity(ProductForPrucharase product, ShoppingCart shoppingCart){
        List<ProductForPrucharase> productList= shoppingCart.getProductsInShoppingCart();
        if(productList.contains(product)){
            int index=productList.indexOf(product);
            ProductForPrucharase productInList=productList.get(index);
            int newQuantity= productInList.getQuantity()+product.getQuantity();
            if(newQuantity>0){
                productInList.setQuantity(newQuantity);
            }
            else {
                removeFromTheShoppingCart(product, shoppingCart);
            }
            return true;
        }
        return false;
    }

    public ShoppingCart createShoppingCart(){
        return this.shopppingCartRepository.save(new ShoppingCart());
    }
}
