package com.kshitij.pocs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToDoNotFoundException extends RuntimeException{
    public ToDoNotFoundException(){
        super("ToDo does not exist");
    }
    public ToDoNotFoundException(String message){
        super(message);
    }
}
