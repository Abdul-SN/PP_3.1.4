package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/admin")
@Controller
public class AdminController {

    public AdminController(){
    }

    @GetMapping("/users")
    public String getAllUsers() {
        return "/admin/users";
    }
}

