package com.cabbage556.learnspringsecurity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityPlayController {

    // CSRF 토큰 생성
    //      CSRF 방지를 위한 동기화 토큰 패턴에서 사용하는 CSRF 토큰 생성
    //      POST, PUT 같은 업데이트 요청 시 CSRF 토큰을 요청 헤더(X-CSRF-TOKEN)에 추가해야 함
    //      세션, 쿠키 방식에서 사용하는 CSRF 방지 방법
    //      stateless REST API에서는 사용하지 않음
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
