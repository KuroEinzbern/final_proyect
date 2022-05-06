package com.finalproyect.controllers;

import com.finalproyect.entities.Users;
import com.finalproyect.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RolesAllowed("user")
    @GetMapping("/user/print")
    public ResponseEntity<Users> printUser(){
        return  new ResponseEntity<>(this.userService.retrieveUser(), HttpStatus.OK);
    }
}
