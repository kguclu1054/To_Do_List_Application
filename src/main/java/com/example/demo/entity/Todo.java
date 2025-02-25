package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "todo")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Kullanıcı ile ilişkilendirme

    @Column(nullable = false)
    private String task;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean completed;
    
    
}


