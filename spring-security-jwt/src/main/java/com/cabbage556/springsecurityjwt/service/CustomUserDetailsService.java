package com.cabbage556.springsecurityjwt.service;

import com.cabbage556.springsecurityjwt.dto.CustomUserDetails;
import com.cabbage556.springsecurityjwt.entity.UserEntity;
import com.cabbage556.springsecurityjwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // loadUserByUsername 메서드 오버라이딩
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 유저 조회
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        // DB에서 유저가 조회된다면 CustomUserDetails 객체 리턴
        return new CustomUserDetails(user);
    }
}
