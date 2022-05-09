package com.finalproyect.services;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.Order;
import com.finalproyect.entities.Users;
import com.finalproyect.model.exceptions.BadOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    public Order buildOrder(){
        Users user= this.userService.retrieveUser();
        Checkout checkout= user.getCheckout();
        String errorMessage="";

        if(checkout.getShoppingCart().getProductsInShoppingCart().isEmpty()) errorMessage+= "La reserva necesita al menos un producto para crear la orden \n";

        if(checkout.getShippingAddress()==null) errorMessage+=("La reserva necesita una direccion de envio para crear la orden \n");

        if(checkout.getPaymentStrategy()==null) errorMessage+=("La reserva necesita un metodo de pago definido para crear la orden");

        if(errorMessage.compareTo("")!=0) throw new BadOrderException(errorMessage);

        Order order= new Order();
        Double finalPrice=checkout.getShoppingCart().getProductsInShoppingCart().stream().mapToDouble(prod->prod.getUnitPrice() * prod.getQuantity()).sum();
        UUID paymentId= this.paymentService.getStrategy(checkout.getPaymentStrategy()).pay();
        order.setTotalCost(finalPrice);
        order.setShippingAddress(checkout.getShippingAddress());
        order.setShoppingCart(checkout.getShoppingCart());
        order.setPaymentId(paymentId);
        return order;
    }
}
