package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class SpringBootSecurityDemoApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private Role adminRole;

	public SpringBootSecurityDemoApplication(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... strings) {

		Role userRole = new Role("ROLE_USER");
		Role adminRole = new Role("ROLE_ADMIN");
		roleRepository.save(userRole);
		roleRepository.save(adminRole);

		User admin = new User("Petr", "Petrov", "admin@mail.ru", "$2y$10$M1doWNCVBhIsT3UF1f3Y5uIwulamsGE8lyUOe.m5WhTtlO/Zj1cqO", 20);
		admin.setRoles(new HashSet<>(List.of(adminRole, userRole)));
		User user = new User("Maksim", "Maksimov", "user@mail.ru", "$2y$10$ctXMdwb5.gOQYziYR0euqeQpB8eWQgby.yJH.3w4.iUSsZck300d2", 30);
		user.setRoles(new HashSet<>(List.of(userRole)));

		this.userRepository.save(admin);
		this.userRepository.save(user);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}
}
