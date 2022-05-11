package com.finalproyect.model.dtos;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.ShippingAddress;
import com.finalproyect.entities.ShoppingCart;
import com.finalproyect.model.patterns.PaymentStrategiesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CheckoutDto {




    private ShoppingCart shoppingCart;

    private ShippingAddress shippingAddress;

    private PaymentStrategiesEnum paymentStrategiesEnum;

    private Long shippingAddressId;



    public CheckoutDto(ShoppingCart shoppingCart, ShippingAddress shippingAddress, PaymentStrategiesEnum paymentStrategiesEnum) {
        this.shoppingCart = shoppingCart;
        this.shippingAddress = shippingAddress;
        this.paymentStrategiesEnum = paymentStrategiesEnum;
    }

    public CheckoutDto(Checkout checkout){
        this.shoppingCart=checkout.getShoppingCart();
        this.paymentStrategiesEnum= checkout.getPaymentStrategy();
        this.shippingAddress=checkout.getShippingAddress();
    }
}
