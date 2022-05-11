package com.finalproyect.controllers;

import com.finalproyect.entities.Order;
import com.finalproyect.model.dtos.OrderDto;
import com.finalproyect.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order/new")
    public ResponseEntity<OrderDto> generateOrder(){
       Order order= orderService.buildOrder();
       return new ResponseEntity<>(new OrderDto(order), HttpStatus.CREATED);
    }


}
