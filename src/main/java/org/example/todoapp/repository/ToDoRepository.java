package org.example.todoapp.repository;

import org.example.todoapp.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    // This repository stores and manages Todo instances using their Long-type IDs
}


/*

Use of @Repository annotation

Interface that extends JpaRepository	❌ Not required @Repository annotation, Spring auto-detects it
Custom repository implementation class	✅ Yes — must annotate manually

 */