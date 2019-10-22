package com.kshitij.pocs.services;

import com.kshitij.pocs.enums.TodoStatus;
import com.kshitij.pocs.models.Todo;
import com.kshitij.pocs.repository.TodoListRepository;
import com.kshitij.pocs.services.TodoListService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class TodoListServiceTest {

    private TodoListService todoListService;

    @Mock
    private TodoListRepository todoListRepository;
    private static Pageable pagable;

    @BeforeClass
    public static void perTest(){
        pagable= PageRequest.of(1,10);
    }
    @Before
    public  void setup(){
        todoListService=new TodoListService(todoListRepository);


    }

    @Test
    public void test_getTodoList(){
        List<Todo> listItems= new ArrayList<>();
        for(int i=0;i<10;i++){
            listItems.add(new Todo(String.format("Do This POC %s",i), TodoStatus.NOTDONE));

        }
        Page<Todo> pagedTasks = new PageImpl(listItems);
        given(todoListRepository.findAll(pagable)).willReturn(pagedTasks);
        List<Todo> todoLists=todoListService.getTodoList(pagable);
        assertThat(todoLists,hasSize(10));
    }
    @Test
    public void test_getTodo(){
        Long todoId=1L;
        Todo todo=new Todo("Do This POC", TodoStatus.NOTDONE);
        given(todoListRepository.findById(todoId)).willReturn(Optional.of(todo));
        //given(todoListRepository.save(newList)).willReturn(newList);
        //TodoList todoLists=todoListService.saveTodoList(newList);
        Todo createdTodo=todoListService.getTodo(todoId);
        log.warn(createdTodo.toString());
        //assertThat(todoLists,isNotNull());
        assertThat(createdTodo.getDescription(),equalTo("Do This POC"));
    }
    @Test
    public void test_addTodo(){
        Todo newTodo=new Todo("This is a new Todo", TodoStatus.NOTDONE);
        given(todoListRepository.save(newTodo)).willReturn(newTodo);
        Todo createdTodo=todoListService.addTodo("This is a new Todo");
        assertThat(createdTodo.getDescription(),equalTo("This is a new Todo"));
        assertThat(createdTodo.getStatus(),equalTo(TodoStatus.NOTDONE));
    }
    @Test
    public void test_updateTodo(){
        Todo newTodo=new Todo("This is a new Todo", TodoStatus.NOTDONE);
        given(todoListRepository.save(newTodo)).willReturn(newTodo);
        Todo updatedTodo=todoListService.updateTodo(newTodo);
        assertThat(updatedTodo.getDescription(),equalTo("This is a new Todo"));
        assertThat(updatedTodo.getStatus(),equalTo(TodoStatus.NOTDONE));
    }

}