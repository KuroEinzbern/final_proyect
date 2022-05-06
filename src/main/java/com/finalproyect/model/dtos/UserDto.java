package com.finalproyect.model.dtos;

import com.finalproyect.entities.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class UserDto {

    private String name;

    @Email
    private String email;


    private CheckoutDto checkoutDto;

    public UserDto(Users users){
        this.checkoutDto= new CheckoutDto(users.getCheckout());
        this.name= users.getName();
        this.email= users.getEmail();
    }
}
