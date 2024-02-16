package com.cabbage556.rest.webservices.restfulwebservices.user;

import com.cabbage556.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.cabbage556.rest.webservices.restfulwebservices.jpa.UserRepository;
import com.cabbage556.rest.webservices.restfulwebservices.post.Post;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        // 리소스에 대한 하이퍼링크를 갖는 HAL 응답 생성
        EntityModel<User> entityModel = EntityModel.of(user.get()); // 사용자 리소스에 대한 HAL 엔티티 모델 생성
        WebMvcLinkBuilder link =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()); // retrieveAllUsers에 대한 링크 생성
        entityModel.add(link.withRel("all-users")); // all-users 라는 이름으로 링크 추가
        return entityModel;
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()  // 현재 요청 URL: /users
                        .path("/{id}")                            // 패스 파라미터 id 추가
                        .buildAndExpand(savedUser.getId())        // 패스 파라미터 id를 savedUser의 id로 치환
                        .toUri();                                 // URI로 생성
        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // 특정 유저의 포스트 조회 요청 처리 메서드
    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts();
    }

    // 특정 유저의 특정 포스트 조회 요청 처리 메서드
    @GetMapping(path = "/jpa/users/{id}/posts/{postId}")
    public Post retrievePostForUser(
            @PathVariable int id,
            @PathVariable int postId
    ) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        return user.get().getPosts()
                .stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst()
                .orElse(null);
    }

    // 특정 유저의 포스트 생성 요청 처리 메서드
    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser(
            @PathVariable int id,
            @Valid @RequestBody Post post
    ) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }
        post.setUser(user.get());  // 포스트를 생성한 유저로 설정
        Post savedPost = postRepository.save(post); // 포스트 저장

        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()  // 현재 요청 URL: /jpa/users/{id}/posts
                        .path("/{id}")                            // 패스 파라미터 id 추가
                        .buildAndExpand(savedPost.getId())        // 패스 파라미터 id를 savedPost의 id로 치환
                        .toUri();                                 // URI로 생성: /jpa/user/{id}/posts/{savedPost id}
        return ResponseEntity
                .created(location)
                .build();
    }
}
