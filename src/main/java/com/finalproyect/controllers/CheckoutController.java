package com.finalproyect.controllers;


import com.finalproyect.entities.Checkout;
import com.finalproyect.model.dtos.CheckoutDto;
import com.finalproyect.model.dtos.ProductDto;
import com.finalproyect.model.exceptions.CheckoutNotFoundException;
import com.finalproyect.services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
public class CheckoutController {

    @Autowired
    CheckoutService checkoutService;

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
            throw new CheckoutNotFoundException("el usuario no posee reservas a su nombre");
    }

    @RolesAllowed("user")
    @GetMapping("/checkout/printcheckout")
    public ResponseEntity<CheckoutDto> printCheckout(){
        Checkout checkout=this.checkoutService.getCurrentCheckout();
        if(checkout==null)throw new CheckoutNotFoundException("El usuario no posee una reserva");
        return new ResponseEntity<>(new CheckoutDto(checkout), HttpStatus.OK);
    }

}
