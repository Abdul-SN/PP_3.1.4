package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
    }
    @GetMapping
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("authorizedUser", userService.findByEmail(principal.getName()));
        return "/user/info";
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public ResponseEntity<User> getCurrentUser(Principal principal) {

        User user = userService.findByEmail(principal.getName());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
