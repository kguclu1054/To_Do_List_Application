package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    public User loginUser(String username, String password) {
        // findByUsername tarafından döndürülen Optional<User> nesnesini işliyoruz
        User user = userRepository.findByUsername(username).orElse(null); 

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}

