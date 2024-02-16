package com.liberty556.learnspringframework.springDI.ex01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan // 패키지 이름을 지정하지 않으면 현재 패키지에서 컴포넌트 스캔을 수행함
public class SimpleSpringContextLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(SimpleSpringContextLauncher.class)) {
            // 현재 스프링 컨텍스트의 스프링 빈 확인
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        }
    }
}
