package com.cabbage556.springsecurityjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      // configuration 클래스
@EnableWebSecurity  // Security 클래스
public class SecurityConfig {

    // bcrypt 해시된 패스워드를 DB에 저장
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 필터 체인 재정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disable csrf
        //      JWT 방식에서 세션은 Stateless하므로 CSRF 보호를 비활성화함
        http
                .csrf((auth) -> auth.disable());

        // disable form login
        http
                .formLogin((auth) -> auth.disable());

        // disable htt basic authentication
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()  // 해당 경로 모든 요청 허용
                        .requestMatchers("/admin").hasRole("ADMIN")           // 해당 경로 ADMIN role 요구
                        .anyRequest().authenticated());                                // 나머지 모든 경로 인증 요구

        // 세션 설정
        //      ****JWT 방식에서 Stateless한 세션 설정****
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        return http.build();
    }
}
