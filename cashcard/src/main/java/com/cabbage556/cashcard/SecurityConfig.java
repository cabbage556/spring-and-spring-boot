package com.cabbage556.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
//      스프링 시큐리티 설정을 위한 스프링 빈 등록
//      스프링에게 이 클래스를 사용해서 스프링과 스프링 부트를 구성하게 함
//      이 클래스에서 명시한 빈은 스프링의 auto configuration 엔진에서 사용 가능하게 됨
@Configuration
class SecurityConfig {

    // 스프링 시큐리티의 Filter Chain을 구성하는 빈
    //      스프링 시큐리티는 Filter Chain을 구성하기 위한 빈을 기대하는데, SecurityFilterChain을 리턴하는 메서드에 @Bean 어노테이션을 적용하면 이 기대를 충족하게 됨
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // enable basic authentication
        //      cashcards/ 엔드포인트로의 모든 HTTP 요청은 HTTP Basic Authentication을 사용해 인증되어야 함
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/cashcards/**")
                        .hasRole("CARD-OWNER"))  // enable RBAC: CARD-OWNER role을 갖는 유저만 접근할 수 있도록 제한함
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // 테스트 목적의 UserDetailsService 구성하기
    //      스프링 IoC 컨테이너는 UserDetailsService를 찾고, 스프링 데이터는 필요에 따라 UserDetailsService를 사용함
    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();

        // Role-Based Access Control(RBAC)
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles("CARD-OWNER")
                .build();
        UserDetails hankOwnsNoCards = users
                .username("hank-owns-no-cards")
                .password(passwordEncoder.encode("qrs456"))
                .roles("NON-OWNER")
                .build();
        UserDetails kumar = users
                .username("kumar2")
                .password(passwordEncoder.encode("xyz789"))
                .roles("CARD-OWNER")
                .build();
        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards, kumar);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
