package com.cabbage556.springboot.learnjpaandhibernate.course.jdbc;

import com.cabbage556.springboot.learnjpaandhibernate.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository  // 클래스가 데이터베이스와 통신하는 역할을 담당함을 명시하는 어노테이션
public class CourseJdbcRepository {

    // Spring JDBC를 사용해 쿼리를 실행하기 위해 JdbcTemplate이 필요함
    @Autowired
    private JdbcTemplate springJdbcTemplate;

    // 정적 변수로 sql 쿼리 선언
    private static String INSERT_QUERY =
            """
            insert into course (id, name, author)
            values (?, ?, ?);
            """;
    private static String DELETE_QUERY =
            """
            delete from course
            where id = ?;
            """;
    private static String SELECT_QUERY =
            """
            select * from course
            where id = ?;
            """;

    // 애플리케이션 시작 단계에서 실행하기
    //      스프링 부트 CommandLineRunner(CourseJdbcCommandLineRunner 클래스)
    public void insert(Course course) {
        springJdbcTemplate.update(
                CourseJdbcRepository.INSERT_QUERY,
                course.getId(),
                course.getName(),
                course.getAuthor()
        );
    }

    public void deleteById(long id) {
        springJdbcTemplate.update(
                CourseJdbcRepository.DELETE_QUERY,
                id
        );
    }

    public Course findById(long id) {
        // SELECT_QUERY에 파라미터 id의 값 사용
        // ResultSet -> Bean : Row Mapper
        //      BeanPropertyRowMapper를 사용해 ResultSet을 Course 클래스로 매핑
        return springJdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
    }
}
