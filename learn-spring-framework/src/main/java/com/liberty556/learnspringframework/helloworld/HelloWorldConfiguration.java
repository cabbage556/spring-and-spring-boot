package com.liberty556.learnspringframework.helloworld;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// 레코드
//      자바 빈을 손쉽게 생성하기 위해 도입
//      setter, getter, 생성자 등이 자동 생성되므로 만들 필요가 없음
record Person (String name, int age, Address address) {};
record Address (String firstLine, String city) {};

// Configuration 클래스
@Configuration
public class HelloWorldConfiguration {

    // Spring 컨테이너가 관리하는 Bean을 생성함
    //      name이라는 Bean을 생성함
    @Bean
    public String name() {
        return "cabbage";
    }

    @Bean
    public int age() {
        return 20;
    }

    @Bean
    public Person person() {
        return new Person("cabbage2", 25, new Address("난향로", "서울"));
    }

    @Bean
    public Person person2MethodCall() {
        // 기존 Spring Bean과 관계가 있는 새로운 Spring Bean 생성
        //      Spring이 관리하는 기존 Bean을 재활용해서 새로운 Bean 생성
        return new Person(name(), age(), address()); // name, age, address Bean
    }

    @Bean
    public Person person3Params(String name, int age, Address address3) { // name, age, address3
        // 기존 Spring Bean과 관계가 있는 새로운 Spring Bean 생성
        //      Spring이 관리하는 기존 Bean을 재활용해서 새로운 Bean 생성
        return new Person(name, age, address3); // name, age, address Bean
    }

    // 일치하는 Bean이 여러 개일 경우 스프링은 예외를 던진다.
    // No qualifying bean of type 'com.liberty556.learnspringframework.helloworld.Address' available:
    //      expected single matching bean but found 2: address2,address3
    @Bean
    @Primary
    public Person person4Params(String name, int age, Address address) { // name, age, address
        // 기존 Spring Bean과 관계가 있는 새로운 Spring Bean 생성
        //      Spring이 관리하는 기존 Bean을 재활용해서 새로운 Bean 생성
        return new Person(name, age, address); // name, age, address Bean
    }

    @Bean
    public Person person5Qualifier(
            String name,
            int age,
            @Qualifier("address3qualifier") Address address // "address3qualifier" 빈을 연결
    ) {
        return new Person(name, age, address);
    }

    // Bean에 사용자 지정 이름 설정 가능
    @Bean(name = "address2")
    @Primary // 기본 bean으로 설정: 여러 bean 후보들 중에서 이 bean을 기본 bean으로 설정함
    public Address address() {
        return new Address("난곡로", "서울");
    }

    @Bean(name = "address3")
    @Qualifier("address3qualifier") // 다른 bean이 qualifier를 통해 이 bean을 사용할 수 있게 함
    public Address address3() {
        return new Address("미성로", "서울");
    }
}
