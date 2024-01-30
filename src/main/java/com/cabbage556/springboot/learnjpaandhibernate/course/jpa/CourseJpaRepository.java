package com.cabbage556.springboot.learnjpaandhibernate.course.jpa;

import com.cabbage556.springboot.learnjpaandhibernate.course.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional  // 트랜잭션 허용: JPA로 쿼리를 실행할 때 트랜잭션 허용 필요
public class CourseJpaRepository {

    // @Autowired 대신 @PersistenceContext 사용
    //      역할에 맞는 구체적인 어노테이션 사용
    //      컨테이너 관리형 EntityManager와 그와 연결된 영속성 컨텍스트와의 의존성을 표현하는 어노테이션
    @PersistenceContext
    private EntityManager entityManager;  // JPA를 활용해 쿼리를 실행하기 위해 EntityManager를 사용함

    // insert 쿼리 실행 메서드
    public void insert(Course course) {
        // EntityManager merge 메서드로 insert 쿼리 실행
        entityManager.merge(course);
    }

    // select 쿼리 실행 메서드
    public Course findById(long id) {
        // EntityManager find 메서드로 select 쿼리 실행
        return entityManager.find(Course.class, id);
    }

    // delete 쿼리 실행 메서드
    public void deleteById(long id) {
        // EntityManager remove 메서드로 delete 쿼리 실행
        Course course = entityManager.find(Course.class, id);
        entityManager.remove(course);
    }
}
