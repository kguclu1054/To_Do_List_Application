package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register işlemi
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";  // register.html sayfasını göster
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                               @RequestParam String password, 
                               @RequestParam String confirmPassword, 
                               @RequestParam String email,  // Burada email'i alıyoruz
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Şifreler eşleşmiyor.");
            return "register";  // Hata mesajıyla birlikte register sayfasına geri dön
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);  // E-posta değerini kaydediyoruz

        userRepository.save(user);
        
        return "redirect:/login";
    }



    // Login işlemi
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // login.html sayfasını göster
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username)
                .orElse(null);  // Kullanıcı bulunamazsa null döner

        if (user != null) {
            // Şifreyi kontrol et
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "redirect:/index";  // Giriş başarılıysa index sayfasına yönlendir
            } else {
                return "redirect:/login?error=true";  // Şifre hatalıysa hata URL'sine yönlendir
            }
        } else {
            return "redirect:/login?error=true";  // Kullanıcı bulunamadıysa hata URL'sine yönlendir
        }
    }





    // Ana sayfa (login başarılıysa)
    @GetMapping("/index")
    public String homePage() {
        return "index";  // Ana sayfa (home.html)
    }
}

