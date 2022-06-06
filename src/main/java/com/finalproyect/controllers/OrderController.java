package com.finalproyect.controllers;

import com.finalproyect.entities.Order;
import com.finalproyect.model.dtos.OrderDto;
import com.finalproyect.model.dtos.OrdersDto;
import com.finalproyect.model.exceptions.OrderNotFoundException;
import com.finalproyect.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/new")
    public ResponseEntity<OrderDto> generateOrder() {
        Order order = orderService.buildOrder();
        return new ResponseEntity<>(new OrderDto(order), HttpStatus.CREATED);
    }

    @GetMapping("/order/print")
    public ResponseEntity<OrdersDto> printOrders(){
        OrdersDto ordersDto=orderService.getOrders();
        if(ordersDto.getOrders().isEmpty()) throw new OrderNotFoundException("este usuario no tiene ordenes registradas");
        return new ResponseEntity<>(ordersDto,HttpStatus.FOUND);
    }
}
