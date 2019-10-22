package com.kshitij.pocs.models;

import com.kshitij.pocs.enums.TodoStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class Todo {
    @Id
    @GeneratedValue
    Long id;
    @NotNull
    String description;
    @NotNull
    TodoStatus status;
    public Todo(String description,TodoStatus todoStatus){
        this.description=description;
        this.status=todoStatus;
    }
}
