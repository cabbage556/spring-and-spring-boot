package com.liberty556.learnspringframework.springDI.ex04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class YourBusinessClass {
    Dependency1 dependency1;
    Dependency2 dependency2;

    // @Autowired : 생성자 기반 의존성 주입에서 생략 가능
    public YourBusinessClass(Dependency1 dependency1, Dependency2 dependency2) {
        System.out.println("constructor injection - YourBusinessClass");
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }

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
public class ConstructorDepInjectionLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ConstructorDepInjectionLauncher.class)) {
            // 현재 스프링 컨텍스트의 스프링 빈 확인
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

            // YourBusinessClass 스프링 빈 확인
            System.out.println("YourBusinessClass 스프링 빈 확인");
            System.out.println(context.getBean(YourBusinessClass.class));
        }
    }
}
