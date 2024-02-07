package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    //Получение списка ВСЕХ пользователей
    //Получение ПУСТОЙ модели пользователя для добавления НОВЫХ
    //Получение авторизованного пользователя для проверки РОЛИ
    @GetMapping("/users")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("newUser", new User());
        model.addAttribute("authorizedUser", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.index());
        return "/admin/users";
    }

    //Получаем из html формы объект User и добавляем его в БД
    @PostMapping("/add")
    public String create(@ModelAttribute("newUser") User user) {
        System.err.println(user.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin/users";
    }
}

