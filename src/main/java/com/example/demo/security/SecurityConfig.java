package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  
            .authorizeRequests()
                .requestMatchers("/login", "/register").permitAll()  
                .anyRequest().authenticated()  
            .and()
            .formLogin()
                .loginPage("/login")  
                .defaultSuccessUrl("/todo", true) 
                .failureUrl("/login?error=true")  
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")  
                .logoutSuccessUrl("/login?logout=true")  
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}










