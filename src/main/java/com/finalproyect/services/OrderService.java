package com.finalproyect.services;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.Order;
import com.finalproyect.entities.Users;
import com.finalproyect.model.dtos.OrdersDto;
import com.finalproyect.model.exceptions.BadOrderException;
import com.finalproyect.repositories.OrderRepository;
import com.finalproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    UserRepository userRepository;

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
        order.setCustomerEmail(user.getEmail());
        order.setCustomerName(user.getName());
        this.orderRepository.save(order);
        this.checkoutService.deleteCurrentCheckout();
        /*user.setCheckout(null);
        this.userRepository.save(user);*/
        return order;
    }

    public OrdersDto getOrders(){
        Users users= this.userService.retrieveUser();
        Iterable<Order> orders= this.orderRepository.findAll();
        List<Order> orderList= new ArrayList<>();
        orders.iterator().forEachRemaining(orderList::add);
        List<Order> finalList=orderList.stream().filter(order ->order.getCustomerEmail().compareTo(users.getEmail())==0).collect(Collectors.toList());
        OrdersDto ordersDto= new OrdersDto(finalList);
        return ordersDto;
    }
}
