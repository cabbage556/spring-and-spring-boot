package com.cabbage556.learnspringsecurity.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationController {

    private JwtEncoder jwtEncoder;

    public JwtAuthenticationController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    private String createToken(Authentication authentication) {
        // JWT 클레임 생성
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .build();
        // JWT 인코더 파라미터 생성
        JwtEncoderParameters params = JwtEncoderParameters.from(claims);
        // JWT 생성
        return jwtEncoder
                .encode(params)
                .getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(" "));
    }
}

record JwtResponse(String token) {}