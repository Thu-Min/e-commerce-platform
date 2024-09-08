package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService service;

}
