package com.cabbage556.springboot.myfirstwebapp.todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todosCount = 0;

    // static 초기화
    static {
        todos.add(new Todo(++todosCount, "cabbage556", "Learn AWS", LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todosCount, "cabbage556", "Learn DevOps", LocalDate.now().plusYears(2), false));
        todos.add(new Todo(++todosCount, "cabbage556", "Learn Database", LocalDate.now().plusYears(3), false));
    }

    public List<Todo> findByUsername(String username) {
        return TodoService.todos.stream()
                .filter(todo -> todo.getUsername().equalsIgnoreCase(username))
                .toList();
    }

    public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
        Todo todo = new Todo(++TodoService.todosCount, username, description, targetDate, done);
        TodoService.todos.add(todo);
    }

    public void deleteById(int id) {
        TodoService.todos.removeIf(todo -> todo.getId() == id);
    }

    public Todo findById(int id) {
        return TodoService.todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .get();
    }

    public void updateTodo(Todo todo) {
        // 삭제 후 그대로 추가
        deleteById(todo.getId());
        todos.add(todo);
    }
}
