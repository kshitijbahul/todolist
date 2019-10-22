package com.kshitij.pocs.controllers;

import com.kshitij.pocs.models.Todo;
import com.kshitij.pocs.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.IntStream;

@RestController
@RequestMapping("todo")
public class TodoListController {
    private TodoListService todoListService;
    @Autowired
    public TodoListController(TodoListService todoListService){
        this.todoListService=todoListService;
    }
    @GetMapping
    private ResponseEntity getTodoList(Pageable pageable){
        return ResponseEntity.ok(this.todoListService.getTodoList(pageable));
    }
    @GetMapping("/{id}")
    private ResponseEntity getTodo(@PathVariable("id") Long todoId){
        return ResponseEntity.ok(this.todoListService.getTodo(todoId));
    }
    @PostMapping
    private ResponseEntity addTodo(@RequestBody String message){
        return ResponseEntity.created(URI.create("")).body(this.todoListService.addTodo(message));
    }
    @PutMapping("/{id}")
    private ResponseEntity updateTodo(@RequestBody Todo todo, @PathVariable("id") Long todoId){
        return ResponseEntity.ok(this.todoListService.updateTodo(todo));
    }
    @DeleteMapping("/{id}")
    private ResponseEntity removeTodo(@PathVariable("id") Long todoId){
        this.todoListService.deleteTodo(todoId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/create/{todocount}")
    private ResponseEntity createSpecifiedTodos(@PathVariable Integer todocount){
        IntStream.range(0,todocount).forEach((count)->{this.todoListService.addTodo(String.format("Buld Gen Todo %d",count));});
        return ResponseEntity.created(URI.create("")).body(this.todoListService.getTodoList(PageRequest.of(0,20)));
    }

}
