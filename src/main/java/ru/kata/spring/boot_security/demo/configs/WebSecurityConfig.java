package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userServiceImpl;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userServiceImpl) {
        this.successUserHandler = successUserHandler;
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Этот блок кода настраивает аутетификацию и авторизацию для приложения.
     * 1. http.authorizeRequests() - определяет, какие запросы требуют аутентификации, а какие нет.
        * В данном случае, мы разрешаем все запросы к "/" и "/index" без аутентификации с помощью antMatchers("/", "/index").permitAll().
     * 2. http.anyRequest().authenticated() - этот метод требует аутентификации для всех других запросов.
     * 3. http.formLogin().successHandler(successUserHandler) - позволяет пользователям войти в систему с помощью формы входа.
        * Мы также разрешаем все запросы кSuccessUserHandler без аутентификации с помощью permitAll().
     * 4. http.logout() - это метод, который позволяет пользователям выйти из системы.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // аутентификация inMemory

    /**
     * UserDetailsService используется Spring Security для получения информации об аутентифицированных пользователях.
     * создаем экземпляр User с именем "user", паролем "user" и ролью "USER".
     * Затем мы используем InMemoryUserDetailsManager для хранения этого пользователя в памяти.
     */


    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userServiceImpl);
        return authenticationProvider;
    }
}