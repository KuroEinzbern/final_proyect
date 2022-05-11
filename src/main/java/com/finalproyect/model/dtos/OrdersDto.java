package com.finalproyect.model.dtos;

import com.finalproyect.entities.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    List<Order> orders;
}
