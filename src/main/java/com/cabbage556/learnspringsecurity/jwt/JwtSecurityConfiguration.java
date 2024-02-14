package com.cabbage556.learnspringsecurity.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class JwtSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        // http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

        // OAuth2 리소스 서버 설정
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }

    // 스프링 부트 OAuth2 리소스 서버를 사용하는 JWT 인증
    // 1. 키 페어 생성하기
    //      비대칭 키 방식의 암호화에서 사용하는 키 페어(공개 키 + 비밀/개인 키) 생성
    @Bean
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");  // RSA 알고리즘을 사용하는 키 페어 생성기
            keyPairGenerator.initialize(2048);                            // 키 사이즈 설정(2048비트 RSA 암호화)
            return keyPairGenerator.generateKeyPair();                            // 키 페어 생성
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // 스프링 부트 OAuth2 리소스 서버를 사용하는 JWT 인증
    // 2. 키 페어를 사용해 RSAKey 객체 생성하기
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())  // 키 페어의 공개 키 설정
                .privateKey(keyPair.getPrivate())             // 키 페어의 비밀/개인 키 설정
                .keyID(UUID.randomUUID().toString())          // 랜덤 UUID를 키 ID로 설정
                .build();                                     // RSAKey 객체 생성
    }


    // 스프링 부트 OAuth2 리소스 서버를 사용하는 JWT 인증
    // 3. JWKSource(JSON Web Key source) 생성하기
    //      RSA 키만 갖는 JWKSet(JSON Web Key Set) 생성
    //      JWKSet를 사용해 JWKSource 생성
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);

        // JWKSource 인터페이스 get 메서드 람다 구현
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

    // 스프링 부트 OAuth2 리소스 서버를 사용하는 JWT 인증
    // 4. 디코딩 시 RSA 공개 키 사용하기(JWT 토큰 디코딩을 위한 디코더 설정)
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }

    // JWT 인코더 생성
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        var user = User
                .withUsername("cabbage556")
                .password("1234")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("USER")
                .build();
        var admin = User
                .withUsername("admin")
                .password("1234")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .roles("ADMIN")
                .build();

        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);

        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
