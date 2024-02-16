package com.liberty556.learnspringframework.gamingSpringBeans2;

import com.liberty556.learnspringframework.game.GameRunner;
import com.liberty556.learnspringframework.game.GamingConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.liberty556.learnspringframework.game") // 스프링이 컴포넌트를 스캔할 패키지 지정
public class App04GamingSpringBeans {

    public static void main(String[] args) {

        try (var context = new AnnotationConfigApplicationContext(App04GamingSpringBeans.class)) {

            // 스프링 컨텍스트에서 GamingConsole 구현 객체(스프링 빈)를 가져올 수 있음
            context.getBean(GamingConsole.class).up();

            // 스프링 컨텍스트에서 GameRunner 객체(스프링 빈)를 가져올 수 있음
            context.getBean(GameRunner.class).run();

        }
    }
}
