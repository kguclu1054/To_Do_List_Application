package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElse(null);
        }
        return null;
    }

    @GetMapping
    public String showTodoPage(Model model) {
        User user = getLoggedInUser();
        if (user == null) return "redirect:/login";

        List<Todo> todos = todoService.getUserTodos(user);
        model.addAttribute("todos", todos);
        model.addAttribute("username", user.getUsername());
        return "index"; 
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String task) {
        User user = getLoggedInUser();
        if (user != null) {
            todoService.addTodo(user, task);
        }
        return "redirect:/todo";
    }

    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todo";
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @RequestParam String newTask, @RequestParam boolean completed) {
        todoService.updateTodoStatus(id, newTask, completed);
        return "redirect:/todo";
    }
}




	

