package com.cabbage556.springsecurityjwt.service;

import com.cabbage556.springsecurityjwt.dto.JoinDTO;
import com.cabbage556.springsecurityjwt.entity.UserEntity;
import com.cabbage556.springsecurityjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);
        if (isExist) {
            return;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));  // BCrypt 해시 패스워드 설정
        user.setRole("ROLE_ADMIN");  // 스프링 시큐리티에서 권한 앞에 "ROLE_" 접두사 추가

        userRepository.save(user);
    }
}
