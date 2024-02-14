package com.cabbage556.learnspringsecurity.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class BasicAuthSecurityConfiguration {

    // 필터 체인 재정의
    //      기본 필터 체인 확인: SpringBootWebSecurityConfiguration.class
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 모든 요청 인증 요구
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        // 세션 생성 정책 stateless 설정 -> 세션 사용 해제
        //      스프링 시큐리티가 HTTP 세션을 생성하지 않고 사용하지 않음
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 기본 로그인 페이지, 로그아웃 페이지 해제
        // http.formLogin(Customizer.withDefaults());

        // 기본 인증
        http.httpBasic(Customizer.withDefaults());

        // CSRF 해제
        http.csrf().disable();

        // h2 콘솔 frame 태그 옵션 활성화
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    // CORS 설정 메서드
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")              // 모든 URL
                        .allowedMethods("*")                       // 모든 요청 메서드
                        .allowedOrigins("http://localhost:3000");  // http://localhost:3000 오리진에 대해 허용
            }
        };
    }

    // 사용자 자격증명 인메모리 저장
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     var user = User
    //             .withUsername("cabbage556")  // username 지정
    //             .password("{noop}1234")      // password 지정, {noop}: 인코딩하지 않음
    //             .roles("USER")               // role 지정
    //             .build();
    //     var admin = User
    //             .withUsername("admin")
    //             .password("{noop}1234")
    //             .roles("ADMIN")
    //             .build();
    //
    //     // 인메모리에 user, admin 자격증명 저장
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // JDBC를 사용해 사용자 자격증명을 저장하기 위한 데이터베이스 설정
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)                         // H2 데이터베이스 타입 지정
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)  // static 변수의 값(경로)에 위치한 user.ddl 스크립트 추가
                .build();
    }

    // 사용자 자격증명을 JDBC를 사용해 저장
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user = User
                .withUsername("cabbage556")  // username 지정
                // .password("{noop}1234")      // password 지정, {noop}: 인코딩하지 않음
                .password("1234")            // password 지정
                .passwordEncoder(str -> passwordEncoder().encode(str))  // password를 passwordEncoder 메서드를 사용해 해싱
                .roles("USER")               // role 지정
                .build();
        var admin = User
                .withUsername("admin")
                // .password("{noop}1234")
                .password("1234")            // password 지정
                .passwordEncoder(str -> passwordEncoder().encode(str))  // password를 passwordEncoder 메서드를 사용해 해싱
                .roles("ADMIN")
                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }

    // BCryptPasswordEncoder
    //      해싱 함수 BCrypt를 사용하는 PasswordEncoder 구현체
    //      패스워드에 대해 해싱을 수행하여 패스워드의 해시 문자열을 얻을 수 있음
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
