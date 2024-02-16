package com.cabbage556.springboot.myfirstwebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        UserDetails userDetails1 = createNewUser("cabbage556", "dummy");
        UserDetails userDetails2 = createNewUser("cabbage557", "dummydummy");
        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails createNewUser(String username, String password) {
        return User.builder()
                .passwordEncoder(input -> passwordEncoder().encode(input))
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain
    //      모든 HttpServletRequest(요청)에 매칭될 수 있는 필터 체인 정의
    //      요청이 들어오면 언제나 먼저 이 필터 체인이 요청을 처리함
    // SecurityFilterChain의 기본 기능 2가지
    //      All URLs are protected
    //      A Login form is shown for unauthorized request
    // H2 console을 사용하기 위한 기능 추가
    //      CSRF disable
    //      H2 console HTML Frames enable
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 기본 기능 다시 정의
        // SecurityFilterChain 오버라이딩 시 기본 기능을 다시 정의해야 함
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()); // 모든 요청 인증 설정
        http.formLogin(Customizer.withDefaults());  // formLogin 활성화: 인증되지 않은 요청의 경우 formLogin으로 username, password 입력 요구

        // 추가 기능 정의
        http.csrf().disable(); // CSRF disable
        http.headers().frameOptions().disable(); // Frame enable

        return http.build();
    }
}
