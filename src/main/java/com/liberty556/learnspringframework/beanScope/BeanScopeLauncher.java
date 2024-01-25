package com.liberty556.learnspringframework.beanScope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
class NormalClass {}

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 프로토타입 클래스 선언
@Component
class PrototypeClass {}

@Configuration
@ComponentScan
public class BeanScopeLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(BeanScopeLauncher.class)) {

            System.out.println("=======NormalClass 스프링 빈=======");
            System.out.println(context.getBean(NormalClass.class)); // NormalClass@68e965f5
            System.out.println(context.getBean(NormalClass.class)); // NormalClass@68e965f5 (동일한 해시코드)
            System.out.println(context.getBean(NormalClass.class)); // NormalClass@68e965f5 (동일한 해시코드)
            System.out.println(context.getBean(NormalClass.class)); // NormalClass@68e965f5 (동일한 해시코드)
            // 스프링 컨텍스트에서 항상 동일한 빈을 가져옴
            //      기본적으로 스프링에서 생성되는 모든 빈은 싱글톤이다.
            //      따라서 빈을 가져올 때마다 동일한 인스턴스가 반환된다.

            System.out.println("=======PrototypeClass 스프링 빈=======");
            System.out.println(context.getBean(PrototypeClass.class)); // PrototypeClass@6f27a732 (서로 다른 해시코드)
            System.out.println(context.getBean(PrototypeClass.class)); // PrototypeClass@6c779568 (서로 다른 해시코드)
            System.out.println(context.getBean(PrototypeClass.class)); // PrototypeClass@f381794 (서로 다른 해시코드)
            System.out.println(context.getBean(PrototypeClass.class)); // PrototypeClass@2cdd0d4b (서로 다른 해시코드)
            System.out.println(context.getBean(PrototypeClass.class)); // PrototypeClass@7e9131d5 (서로 다른 해시코드)
            // 스프링 컨텍스트에서 항상 새로운 빈을 가져옴
            //      프로토타입 클래스의 경우 스프링 컨텍스트에서 빈을 가져올 때마다 새로운 빈이 생성되어 반환된다.
        }
    }
}
