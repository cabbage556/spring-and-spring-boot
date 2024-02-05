package com.cabbage556.rest.webservices.restfulwebservices.post;

import com.cabbage556.rest.webservices.restfulwebservices.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 10)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)  // User 엔티티와 다대일 관계 설정, FetchType.LAZY: 게시글 정보 조회 시 사용자 정보를 함께 조회하지 않음
    private User user;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", desription='" + description + '\'' +
                '}';
    }
}
