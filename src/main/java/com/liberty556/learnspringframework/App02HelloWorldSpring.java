package com.liberty556.learnspringframework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App02HelloWorldSpring {
    public static void main(String[] args) {

        // 1: launch a Spring Context
        //      JVM에 Spring Context 생성
        //      Spring Context를 생성하기 위해 @Configuration 클래스 사용
        var context = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);

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

        //      Bean 타입으로 Bean 가져오기
        // System.out.println(context.getBean(Address.class)); // Address[firstLine=난곡로, city=서울]
    }
}
