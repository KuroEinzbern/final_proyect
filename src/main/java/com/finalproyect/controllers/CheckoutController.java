package com.finalproyect.controllers;


import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.Users;
import com.finalproyect.model.exceptions.CheckoutNotFoundException;
import com.finalproyect.model.dtos.CheckoutDto;
import com.finalproyect.model.dtos.ProductDto;
import com.finalproyect.model.dtos.UserDto;
import com.finalproyect.services.CheckoutService;
import com.finalproyect.services.PaymentService;
import com.finalproyect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class CheckoutController {

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;


    @RolesAllowed("user")
    @PostMapping("/checkout/newcheckout")
    public ResponseEntity<CheckoutDto> createCheckout(@RequestBody CheckoutDto checkoutDto){
        Checkout newCheckout=checkoutService.createCheckout(checkoutDto);
        return new ResponseEntity<>(new CheckoutDto(newCheckout), HttpStatus.CREATED);
    }

    @RolesAllowed("user")
    @PatchMapping("/checkout/updatecheckoutinfo")
    public ResponseEntity<CheckoutDto> updateCheckout(@RequestBody CheckoutDto checkoutDto){
        Checkout updatedCheckout=this.checkoutService.updateCheckout(checkoutDto);
        return new ResponseEntity<>(new CheckoutDto(updatedCheckout),HttpStatus.OK);
    }

    @RolesAllowed("user")
    @PatchMapping("/checkout/addproducts")
    public ResponseEntity<CheckoutDto> addProduct( @RequestBody ProductDto productDto){
        if(checkoutService.checkoutIsNotNull()) {
            this.checkoutService.addProduct(productDto);
            return new ResponseEntity<>(new CheckoutDto(this.checkoutService.getCurrentCheckout()), HttpStatus.OK);
        }
        else {
            Users users= this.userService.retrieveUser();
            throw new CheckoutNotFoundException("el usuario" + users.getEmail() + "con id" + users.getUserId()+ "no tiene ninguna reserva a su nombre");
        }
    }

    @RolesAllowed("user")
    @GetMapping("/checkout/printcheckout")
    public ResponseEntity<UserDto> printCheckout(){
        Users users = this.userService.retrieveUser();
        return new ResponseEntity<>(new UserDto(users), HttpStatus.OK);
    }

}
