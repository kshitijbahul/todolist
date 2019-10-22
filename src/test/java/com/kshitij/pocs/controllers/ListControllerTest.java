package com.kshitij.pocs.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kshitij.pocs.enums.TodoStatus;
import com.kshitij.pocs.models.Todo;
import com.kshitij.pocs.services.TodoListService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@WebMvcTest
public class ListControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoListService todoListService;


    private static List<Todo> listItems= new ArrayList<>();
    private static Pageable pageable;

    @BeforeClass
    public static void beforeEachTest(){
        listItems.add(new Todo("Do This POC", TodoStatus.NOTDONE));
        pageable= PageRequest.of(1,10);
    }
    /*
    Todo List per user
    ToDo List Items per Todo List
     */

    @Test
    public void test_addTodo() throws Exception {

        given(todoListService.addTodo("My Todo Item")).willReturn(new Todo("My Todo Item", TodoStatus.NOTDONE));
        //given(todoListService.getTodoList(pageable)).willReturn(listItems);
        mockMvc.perform(MockMvcRequestBuilders.post("/todo").content("My Todo Item")).andExpect(status().isCreated())
                .andExpect(jsonPath("description",equalTo("My Todo Item")))
                .andExpect(jsonPath("status",equalTo(TodoStatus.NOTDONE.name())));
    }
    @Test
    public void test_getTodoList() throws Exception {

        given(todoListService.getTodoList(pageable)).willReturn(listItems);
        mockMvc.perform(MockMvcRequestBuilders.get("/todo").param("page","1").param("size","10")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(1)));
    }
    @Test
    public void test_getTodo() throws Exception {
        Todo todo=new Todo("Do This POC", TodoStatus.NOTDONE);
        Long todoId=1L;
        given(todoListService.getTodo(todoId)).willReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/todo/%s",todoId))).andExpect(status().isOk())
                .andExpect(jsonPath("description",equalTo(todo.getDescription())))
                //.andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("status",equalTo(TodoStatus.NOTDONE.name())));
    }
    @Test
    public void test_removeTodo() throws Exception {

        //todoList.add();
        Long todoId=1L;
        //TodoList todoList= new TodoList(todoId,listItems);
        //given(todoListService.deleteTodo(todoId));
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/todo/%s",todoId))).andExpect(status().isAccepted());
        //.andExpect(content().string(todoId.toString()));
    }
    @Test
    public void test_updateTodo() throws Exception {
        Long todoId=1L;
        Todo todo= new Todo("Do This POC", TodoStatus.NOTDONE);
        given(todoListService.updateTodo(todo)).willReturn(todo);
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/todo/%s",todoId)).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(todo))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("status",equalTo(TodoStatus.NOTDONE.name())));
    }
}