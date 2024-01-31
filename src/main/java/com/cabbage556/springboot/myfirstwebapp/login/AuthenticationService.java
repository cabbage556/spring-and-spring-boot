package com.cabbage556.springboot.myfirstwebapp.login;

import org.springframework.stereotype.Service;

@Service  // 서비스 로직을 갖는 스프링 빈임을 나타내는 어노테이션
public class AuthenticationService {

    public boolean authenticate(String username, String password) {
        // 인증(하드코딩 데이터)
        boolean isValidUsername = username.equalsIgnoreCase("cabbage556");
        boolean isValidPassword = password.equalsIgnoreCase("dummy");
        return isValidUsername && isValidPassword;
    }
}
