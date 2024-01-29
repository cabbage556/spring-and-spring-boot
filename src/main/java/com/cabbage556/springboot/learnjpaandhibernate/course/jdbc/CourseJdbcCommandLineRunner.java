package com.cabbage556.springboot.learnjpaandhibernate.course.jdbc;

import com.cabbage556.springboot.learnjpaandhibernate.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// CommandLineRunner 인터페이스
//      스프링 빈이 SpringApplication 안에 포함되어 있을 때 실행되어야 함을 나타내기 위해 사용하는 인터페이스
//      즉 스프링 애플리케이션 시작 시 실행될 로직이 있을 때 CommandLineRunner 인터페이스 활용 가능
@Component
public class CourseJdbcCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CourseJdbcRepository repository;

    // 애플리케이션 시작 시 실행
    @Override
    public void run(String... args) throws Exception {
        repository.insert(new Course(1, "Learn AWS", "in28minutes")); // insert 쿼리 실행
        repository.insert(new Course(2, "Learn Azure", "in28minutes")); // insert 쿼리 실행
        repository.insert(new Course(3, "Learn DevOps", "in28minutes")); // insert 쿼리 실행

        repository.deleteById(1); // delete 쿼리 실행

        System.out.println(repository.findById(2)); // select 쿼리 실행
        System.out.println(repository.findById(3)); // select 쿼리 실행
    }
}
