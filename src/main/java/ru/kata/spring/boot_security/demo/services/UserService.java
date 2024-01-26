package ru.kata.spring.boot_security.demo.services;




import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {

    public User findByEmail(String email);

    public List<User> index();

    User getById(Long id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);


}
