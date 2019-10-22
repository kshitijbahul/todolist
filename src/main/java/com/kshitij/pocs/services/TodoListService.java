package com.kshitij.pocs.services;

import com.kshitij.pocs.enums.TodoStatus;
import com.kshitij.pocs.exceptions.ToDoNotFoundException;
import com.kshitij.pocs.models.Todo;
import com.kshitij.pocs.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {

    private TodoListRepository todoListRepository;
    @Autowired
    public TodoListService(TodoListRepository todoListRepository){
        this.todoListRepository=todoListRepository;
    }

    public Todo addTodo(String my_todo_item) {
        return this.todoListRepository.save(new Todo(my_todo_item, TodoStatus.NOTDONE));
    }

    public List<Todo> getTodoList(Pageable pageable) {
        return this.todoListRepository.findAll(PageRequest.of(pageable.getPageNumber(),pageable.getPageSize())).getContent();
    }

    public void deleteTodo(Long todoId) {
        try{
            this.todoListRepository.deleteById(todoId);
        }catch(EmptyResultDataAccessException exc){
            throw new ToDoNotFoundException();
        }

    }

    public Todo updateTodo(Todo todo) {
        return this.todoListRepository.save(todo);
    }

    public Todo getTodo(Long todoId) {
        return this.todoListRepository.findById(todoId).orElseThrow(()->new ToDoNotFoundException(""));
    }
}
