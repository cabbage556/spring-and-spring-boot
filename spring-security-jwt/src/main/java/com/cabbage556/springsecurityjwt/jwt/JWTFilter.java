package com.cabbage556.springsecurityjwt.jwt;

import com.cabbage556.springsecurityjwt.dto.CustomUserDetails;
import com.cabbage556.springsecurityjwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 검증 필터
public class JWTFilter extends OncePerRequestFilter {

    // JWTUtil 의존성
    private JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // JWT 검증 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request에서 Authorization 헤더를 가져옴
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            // 다음 필터를 실행하는 것보다 JWT 검증 실패에 대한 적절한 응답을 생성해서 리턴하기
            filterChain.doFilter(request, response); // 다음 필터로 request, response 넘기기
            return;
        }

        // JWT 획득: "Bearer " 제거
        String token = authorization.split(" ")[1];

        // 만료시간 검증
        if (jwtUtil.isExpired(token)) {
            // 다음 필터를 실행하는 것보다 JWT 검증 실패에 대한 적절한 응답을 생성해서 리턴하기
            filterChain.doFilter(request, response);
            return;
        }

        // username, role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // UserEntity 생성
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword("tempPassword");   // 모든 요청마다 DB에서 패스워드를 조회하지 않도록 비밀번호의 경우 임시 값 할당
        user.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록(인증 토큰 등록)
        //      현재 요청에 대한 유저 세션을 생성하는 것
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
