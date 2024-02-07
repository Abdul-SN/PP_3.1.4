package ru.kata.spring.boot_security.demo.configs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    /**
    * Добавляем контроллер просмотра для URL "/index" и устанавливаем  представления на "index".
    */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/index", "/");
        registry.addViewController("/").setViewName("/user/greeting");
    }
}
