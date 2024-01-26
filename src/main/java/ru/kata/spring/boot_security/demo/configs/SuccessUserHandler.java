package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.

    /**
    * В методе onAuthenticationSuccess(), мы получаем объект Authentication,
    * который содержит инфомацию об аутенфицированном пользователе. Мы извлекаем набор ролей из объекта Authentication
    * с помощью authentication.getAuthorities(). Затем мы проверяем, содержит ли набор ролей роль "ROLE_USER".
    * Если это так, мы перенаправляем пользователя на страницу "/user".
    * Если нет, мы перенаправляем пользователя на главную страницу "/".
    */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/users");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        }
    }
}