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
                .requestMatchers("/login", "/register").permitAll()  // login ve register'a herkes erişebilir
                .anyRequest().authenticated()  // Diğer tüm isteklere yetki gereklidir
            .and()
            .formLogin()
                .loginPage("/login")  // Özel giriş sayfası
                .defaultSuccessUrl("/index", true)  // Giriş başarılı olursa index sayfasına yönlendir
                .failureUrl("/login?error=true")  // Hata durumunda login sayfasına yönlendir
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")  // Logout URL'si
                .logoutSuccessUrl("/login?logout=true")  // Çıkış sonrası login sayfasına yönlendir
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Şifrelerin güvenli bir şekilde saklanabilmesi için BCrypt kullanılıyor
    }
}









