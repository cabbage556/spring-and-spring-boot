package com.cabbage556.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }
        return user;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);

        // 생성된 유저 리소스를 확인할 수 있는 URI 생성
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()  // 현재 요청 URL: /users
                        .path("/{id}")                            // 패스 파라미터 id 추가
                        .buildAndExpand(savedUser.getId())        // 패스 파라미터 id를 savedUser의 id로 치환
                        .toUri();                                 // URI로 생성

        // 상태 코드 설정: 201 Created
        // location 응답 헤더 설정: 생성된 리소스 URI 할당
        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.deleteOneById(id);
    }
}
