package com.cabbage556.springsecurityjwt.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    // JWT 의존성 0.12.3 버전 방식
    private SecretKey secretKey;

    // application.properties에 등록된 spring.jwt.secret 값을 key에 삽입
    public JWTUtil(@Value("${spring.jwt.secret}") String secretKey) {
        // HS256 암호화 알고리즘(양방향 대칭), key를 사용해 시크릿 키 생성
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)              // 생성한 시크릿 키를 사용해 토큰 검증
                .build()
                .parseSignedClaims(token)           // 토큰에서 클레임 파싱
                .getPayload()                       // 페이로드로부터
                .get("username", String.class);  // username 클레임 값 가져오기
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)              // 생성한 시크릿 키를 사용해 토큰 검증
                .build()
                .parseSignedClaims(token)           // 토큰에서 클레임 파싱
                .getPayload()                       // 페이로드로부터
                .get("role", String.class);      // role 클레임 값 가져오기
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()        // 만료일 가져오기
                .before(new Date());    // 현재 시간 이전인지 확인
    }

    // JWT 발급 메서드
    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)                                // username 클레임 설정
                .claim("role", role)                                        // role 클레임 설정
                .issuedAt(new Date(System.currentTimeMillis()))                // 발급시간 클레임 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs))  // 만료시간 클레임 설정
                .signWith(secretKey)                                           // secretKey로 시그니처 암호화
                .compact();
    }
}
