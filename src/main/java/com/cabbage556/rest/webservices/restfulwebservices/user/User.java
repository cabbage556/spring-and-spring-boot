package com.cabbage556.rest.webservices.restfulwebservices.user;

import com.cabbage556.rest.webservices.restfulwebservices.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details")  // user가 h2 db 키워드이므로 user_details로 테이블 이름 설정
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    // @JsonProperty("user_name")
    @Size(min = 2, message = "name should have at least 2 characters")  // 길이 유효성 검증
    private String name;

    // @JsonProperty("birth_date")
    @Past(message = "birthDate should be in the past")  // 과거 시점 유효성 검증
    private LocalDate birthDate;

    @JsonIgnore  // 유저 빈 응답에서 posts 필드 제외
    @OneToMany(mappedBy = "user")  // Post 엔티티와 일대다 관계 설정, Post 엔티티에서 관계를 갖는 필드 이름 지정
    private List<Post> posts;

    public User() {}

    public User(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
