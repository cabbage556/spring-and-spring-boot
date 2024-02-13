package com.cabbage556.learnspringsecurity.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
