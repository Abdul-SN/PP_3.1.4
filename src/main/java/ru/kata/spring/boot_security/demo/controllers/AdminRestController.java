package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class AdminRestController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Long userId){
        return userService.getById(userId);
    }

    @PostMapping("/update")
    public User update(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.updateUser(user);
    }

    @PostMapping("/delete/{userId}")
    public User deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
