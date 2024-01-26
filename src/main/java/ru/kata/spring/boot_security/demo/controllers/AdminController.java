package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

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
    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.index());
        return "/admin/users";
    }

    //Создаем пустой объект пользователя, передаем модель user в post метод (create)
    @GetMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        return "/admin/add";
    }

    //Получаем из html формы объект User и добавляем его в БД
    @PostMapping("/users")
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    //Отправка формы для редактирования  пользователя
    @GetMapping("/users/update")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/update";
    }

    //Получение User`а с edit метода, изменение полей, и вывод
    @PostMapping("/users/update")
    public String updateUser(@RequestParam(value = "id") Long id,
                             @ModelAttribute("user") User user) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    //Удаление пользователя по id
    @PostMapping("users/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}

