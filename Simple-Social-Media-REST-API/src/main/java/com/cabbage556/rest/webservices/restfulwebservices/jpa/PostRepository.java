package com.cabbage556.rest.webservices.restfulwebservices.jpa;

import com.cabbage556.rest.webservices.restfulwebservices.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
