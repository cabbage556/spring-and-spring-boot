package com.cabbage556.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

// @Controller
@SessionAttributes("name")  // WelcomeController에서 세션에 추가한 name 데이터를 가져오기 위해 사용
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        List<Todo> todos = todoService.findByUsername(getLoggedInUsername());
        model.put("todos", todos);

        return "listTodos";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        // Todo 생성 후 todo.jsp의 form 태그에 바인딩: Controller -> todo.jsp form (빈 to form 바인딩)
        Todo todo = new Todo(0, getLoggedInUsername(), "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);

        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodo(
            ModelMap model,
            @Valid Todo todo,  // Todo 빈에 바인딩: todo.jsp form -> todo (form to 빈 바인딩) / @Valid: Bean 바인딩 검증 어노테이션
            BindingResult result
    ) {
        // 검증 에러 발생 시 todo 페이지로
        if (result.hasErrors()) {
            return "todo";
        }

        todoService.addTodo(getLoggedInUsername(), todo.getDescription(), todo.getTargetDate(), false);

        // /list-todos URL로 리다이렉트
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoService.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        // todo.jsp의 form 태그에 바인딩
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);

        return "todo";
    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateTodo(
            ModelMap model,
            @Valid Todo todo,  // Todo 빈에 바인딩: todo.jsp form -> todo (form to 빈 바인딩) / @Valid: Bean 바인딩 검증 어노테이션
            BindingResult result
    ) {
        // 검증 에러 발생 시 todo 페이지로
        if (result.hasErrors()) {
            return "todo";
        }

        // 업데이트
        todo.setUsername(getLoggedInUsername());
        todoService.updateTodo(todo);

        // /list-todos URL로 리다이렉트
        return "redirect:list-todos";
    }

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
