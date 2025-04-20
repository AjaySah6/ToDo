package org.example.todoapp.repository;

import org.example.todoapp.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    // This repository stores and manages Todo instances using their Long-type IDs
}
