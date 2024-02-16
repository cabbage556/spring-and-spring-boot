package com.cabbage556.springboot.learnjpaandhibernate.course;

import com.cabbage556.springboot.learnjpaandhibernate.course.jdbc.CourseJdbcRepository;
import com.cabbage556.springboot.learnjpaandhibernate.course.jpa.CourseJpaRepository;
import com.cabbage556.springboot.learnjpaandhibernate.course.springdatajpa.CourseSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// CommandLineRunner 인터페이스
//      스프링 빈이 SpringApplication 안에 포함되어 있을 때 실행되어야 함을 나타내기 위해 사용하는 인터페이스
//      즉 스프링 애플리케이션 시작 시 실행될 로직이 있을 때 CommandLineRunner 인터페이스 활용 가능
@Component
public class CourseCommandLineRunner implements CommandLineRunner {

    // JDBC 레포지토리 의존성
    // @Autowired
    // private CourseJdbcRepository repository;

    // JPA 레포지토리 의존성
    // @Autowired
    // private CourseJpaRepository repository;

    // Spring Data JPA 레포지토리 의존성
    @Autowired
    private CourseSpringDataJpaRepository repository;

    // 애플리케이션 시작 시 실행
    @Override
    public void run(String... args) throws Exception {
        // JDBC, JPA insert
        // repository.insert(new Course(1, "Learn AWS", "in28minutes")); // insert 쿼리 실행
        // repository.insert(new Course(2, "Learn Azure", "in28minutes")); // insert 쿼리 실행
        // repository.insert(new Course(3, "Learn DevOps", "in28minutes")); // insert 쿼리 실행

        // Spring Data JPA insert
        repository.save(new Course(1, "Learn AWS", "in28minutes"));
        repository.save(new Course(2, "Learn Azure", "in28minutes"));
        repository.save(new Course(3, "Learn DevOps", "in28minutes"));

        // Spring Data JPA deleteById 메서드 제공
        repository.deleteById(1L); // delete 쿼리 실행

        // Spring Data JPA findById 메서드 제공
        System.out.println(repository.findById(2L)); // select 쿼리 실행
        System.out.println(repository.findById(3L)); // select 쿼리 실행

        // 다양한 메서드 제공
        System.out.println(repository.findAll());
        System.out.println(repository.count());

        // 커스텀 메서드 사용
        System.out.println(repository.findByAuthor("in28minutes"));
        System.out.println(repository.findByAuthor(""));
        System.out.println(repository.findByName("Learn AWS"));
        System.out.println(repository.findByName("Learn DevOps"));
    }
}
