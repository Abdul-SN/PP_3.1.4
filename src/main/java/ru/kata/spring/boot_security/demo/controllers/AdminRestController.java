package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
         List<User> allUsers = userService.index();

        if (allUsers != null) {
            return ResponseEntity.ok(allUsers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser(Principal principal) {

        User user = userService.findByEmail(principal.getName());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
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
