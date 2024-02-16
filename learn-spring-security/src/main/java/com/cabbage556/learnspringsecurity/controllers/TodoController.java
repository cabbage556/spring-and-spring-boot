package com.cabbage556.learnspringsecurity.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static List<Todo> TODOS_LIST = List.of(
            new Todo("cabbage556", "Learn AWS"),
            new Todo("cabbage556", "Learn Spring Boot")
    );

    @GetMapping(path = "/todos")
    public List<Todo> getAllTodos() {
        return TodoController.TODOS_LIST;
    }

    @GetMapping(path = "/users/{username}/todos")
    public Todo getTodosForASpecificUser(@PathVariable("username") String username) {
        return TodoController.TODOS_LIST.get(0);
    }

    @PostMapping(path = "/users/{username}/todos")
    public void createTodoForASpecificUser(@PathVariable("username") String username, @RequestBody Todo todo) {
        logger.info("Create {} for {}", todo, username);
    }

}

record Todo(String username, String description) {}