package com.liberty556.learnspringframework.springDI.ex02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class YourBusinessClass {
    // 필드 기반 의존성 주입: 리플렉션 사용
    @Autowired
    Dependency1 dependency1;

    // 필드 기반 의존성 주입: 리플렉션 사용
    @Autowired
    Dependency2 dependency2;

    @Override
    public String toString() {
        return "Using " + dependency1 + " and " + dependency2;
    }
}

@Component
class Dependency1 {}

@Component
class Dependency2 {}

@Configuration
@ComponentScan // 패키지 이름을 지정하지 않으면 현재 패키지에서 컴포넌트 스캔을 수행함
public class FieldDepInjectionLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(FieldDepInjectionLauncher.class)) {
            // 현재 스프링 컨텍스트의 스프링 빈 확인
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

            // YourBusinessClass 스프링 빈 확인
            System.out.println("YourBusinessClass 스프링 빈 확인");
            System.out.println(context.getBean(YourBusinessClass.class));
        }
    }
}
