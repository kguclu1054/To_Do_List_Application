package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Todo;
import com.example.demo.entity.User;

public interface TodoRepository extends JpaRepository<Todo, Long>{


	List<Todo> findByUser(User user);
	
}
