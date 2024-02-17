package com.cabbage556.springsecurityjwt.config;

import com.cabbage556.springsecurityjwt.jwt.JWTUtil;
import com.cabbage556.springsecurityjwt.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration      // configuration 클래스
@EnableWebSecurity  // Security 클래스
public class SecurityConfig {

    // AuthenticationManager 의존성 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;

    // LoginFilter 의존성 생성자 주입
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    // bcrypt 해시된 패스워드를 DB에 저장하기 위해 스프링 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 스프링 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // JWT 기반 인증 및 인가에 사용할 필터 체인 재정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // disable csrf
        //      JWT 방식에서 세션은 Stateless하므로 CSRF 보호를 비활성화함
        http
                .csrf((auth) -> auth.disable());

        // disable form login
        //      UsernamePasswordAuthenticationFilter가 비활성화됨 -> LoginFilter 구현
        http
                .formLogin((auth) -> auth.disable());

        // disable http basic authentication
        //      Basic 토큰이 아닌 JWT 사용
        http
                .httpBasic((auth) -> auth.disable());

        // LoginFilter 추가
        //      UsernamePasswordAuthenticationFilter 자리에 추가
        http
                .addFilterAt(
                        new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                        UsernamePasswordAuthenticationFilter.class
                );

        // 경로별 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()  // 해당 경로 모든 요청 허용
                        .requestMatchers("/admin").hasRole("ADMIN")           // 해당 경로 ADMIN role 요구
                        .anyRequest().authenticated()                                  // 나머지 모든 경로 인증 요구
                );

        // 세션 설정
        //      ****JWT 방식에서 Stateless한 세션 설정****
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        return http.build();
    }
}
