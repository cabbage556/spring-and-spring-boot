package com.cabbage556.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    // Spring HATEOAS: HAL 응답 생성하기
    //      EntityModel + WebMvcLinkBuilder
    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }

        // 리소스에 대한 하이퍼링크를 갖는 HAL 응답 생성
        EntityModel<User> entityModel = EntityModel.of(user); // 사용자 리소스에 대한 HAL 엔티티 모델 생성
        WebMvcLinkBuilder link =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()); // retrieveAllUsers에 대한 링크 생성
        entityModel.add(link.withRel("all-users")); // all-users 라는 이름으로 링크 추가
        return entityModel;
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
