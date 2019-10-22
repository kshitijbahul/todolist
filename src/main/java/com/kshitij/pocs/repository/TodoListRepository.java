package com.kshitij.pocs.repository;

import com.kshitij.pocs.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<Todo, Long> {
}
