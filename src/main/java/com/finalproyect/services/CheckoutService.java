package com.finalproyect.services;

import com.finalproyect.entities.*;
import com.finalproyect.model.dtos.CheckoutDto;
import com.finalproyect.model.dtos.ProductDto;
import com.finalproyect.repositories.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;


    public void addProduct(ProductDto productDto, Checkout currentCheckout) {
            ProductForPrucharase product = this.productService.retrieveProduct(productDto.getProductCode(), productDto.getQuantity());
            this.shoppingCartService.addToShoppingCart(product, currentCheckout.getShoppingCart());
            if (currentCheckout.getShoppingCart().getProductsInShoppingCart().isEmpty()) {
                this.checkoutRepository.delete(currentCheckout);
            }
            this.checkoutRepository.save(currentCheckout);
        }


    public Checkout createCheckout(CheckoutDto checkoutDto, User user) {
        Checkout newCheckout = new Checkout();
        newCheckout.setPaymentStrategy(checkoutDto.getPaymentStrategiesEnum());
        newCheckout.setShippingAddress(checkoutDto.getShippingAddress());
        userService.addNewCheckout(user,newCheckout);
        return this.checkoutRepository.save(newCheckout);
    }

    public Checkout updateCheckout(CheckoutDto checkoutDto, Checkout currentCheckout) {
        currentCheckout.setShippingAddress(checkoutDto.getShippingAddress() != null ? checkoutDto.getShippingAddress() : currentCheckout.getShippingAddress());
        if (checkoutDto.getPaymentStrategiesEnum() != null)
            currentCheckout.setPaymentStrategy(checkoutDto.getPaymentStrategiesEnum());
        return this.checkoutRepository.save(currentCheckout);
    }

}
