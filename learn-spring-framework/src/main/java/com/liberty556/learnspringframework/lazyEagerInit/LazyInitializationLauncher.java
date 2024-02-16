package com.liberty556.learnspringframework.lazyEagerInit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class ClassA {}

@Component
@Lazy // 스프링 빈을 지연하여 초기화함, 즉 빈에 접근하거나 빈을 사용할 때 초기화됨
class ClassB {

    private ClassA classA;

    public ClassB(ClassA classA) {
        System.out.println("Some Initialization logic here...");
        this.classA = classA;
    }

    public void doSomething() {
        System.out.println("ClassB.doSomething");
    }
}

@Configuration
@ComponentScan
public class LazyInitializationLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(LazyInitializationLauncher.class)) {
            // Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

            System.out.println("컨텍스트 초기화 완료");

            // ClassB 빈을 사용 -> @Lazy이므로 이 시점에 초기화
            context.getBean(ClassB.class).doSomething();
        }
    }
}
