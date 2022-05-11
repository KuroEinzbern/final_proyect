package com.finalproyect.model.dtos;

import com.finalproyect.entities.Order;
import com.finalproyect.entities.ShippingAddress;
import com.finalproyect.entities.ShoppingCart;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    private ShoppingCart shoppingCart;

    private ShippingAddress shippingAddress;

    private Double totalCost;

    private UUID paymentId;

    private String customerEmail;

    private String customerName;

    public OrderDto(Order order){
        this.shoppingCart=order.getShoppingCart();
        this.shippingAddress=order.getShippingAddress();
        this.totalCost=order.getTotalCost();
        this.paymentId=order.getPaymentId();
        this.customerEmail= order.getCustomerEmail();
        this.customerName=order.getCustomerName();
    }
}
