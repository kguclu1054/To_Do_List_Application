package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getUserTodos(User user) {
        return todoRepository.findByUser(user); // Kullanıcıya özel görevleri getir
    }

    public Todo addTodo(User user, String task) {
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTask(task);
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public void updateTodoStatus(Long id, String newTask, boolean completed) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.setTask(newTask);
        todo.setCompleted(true);
        todoRepository.save(todo);
    }
}

 
