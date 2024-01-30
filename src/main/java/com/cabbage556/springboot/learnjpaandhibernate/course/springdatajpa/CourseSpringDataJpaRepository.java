package com.cabbage556.springboot.learnjpaandhibernate.course.springdatajpa;

import com.cabbage556.springboot.learnjpaandhibernate.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA 사용 시 인터페이스를 활용함
//      JpaRepository 제네릭 인터페이스를 상속함<엔티티, ID필드타입>
public interface CourseSpringDataJpaRepository extends JpaRepository<Course, Long> {

    // 커스텀 메서드 정의 가능
    //      네이밍 컨벤션을 따라야 함
    List<Course> findByAuthor(String author);  // author 필드로 행 검색
    List<Course> findByName(String name);  // name 필드로 행 검색
}
