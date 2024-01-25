package com.liberty556.learnspringframework.helloworld;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class App02HelloWorldSpring {
    public static void main(String[] args) {

        // 1: launch a Spring Context
        //      JVM에 Spring Context 생성
        //      Spring Context를 생성하기 위해 @Configuration 클래스 사용
        try (var context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class)) {
            // 2: configure the things that we want Spring to manage - @Configuration
            //      HelloWorldConfiguration - @Configuration
            //          String name() - @Bean
            //          ...

            // 3: Retrieves Beans managed by Spring
            //      Bean 이름으로 Bean 가져오기
            System.out.println(context.getBean("name")); // cabbage
            System.out.println(context.getBean("age")); // 20
            System.out.println(context.getBean("person")); // Person[name=cabbage2, age=25, address=Address[firstLine=난향로, city=서울]]
            System.out.println(context.getBean("person2MethodCall")); // Person[name=cabbage, age=20, address=Address[firstLine=난곡로, city=서울]]
            System.out.println(context.getBean("person3Params")); // Person[name=cabbage, age=20, address=Address[firstLine=미성로, city=서울]]
            System.out.println(context.getBean("address2")); // Address[firstLine=난곡로, city=서울]
            System.out.println(context.getBean("person5Qualifier"));

            //      Bean 타입으로 Bean 가져오기
            System.out.println(context.getBean(Person.class));  // 기본 bean을 가져옴
            System.out.println(context.getBean(Address.class)); // 기본 bean을 가져옴

            // Spring이 관리하는 모든 Bean 나열하기
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        }
    }
}
