package com.finalproyect.model.dtos;

import com.finalproyect.entities.User;
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

    public UserDto(User user){
        this.checkoutDto= new CheckoutDto(user.getCheckout());
        this.name= user.getName();
        this.email= user.getEmail();
    }
}
