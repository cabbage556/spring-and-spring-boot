package com.liberty556.learnspringframework.springDI.ex05;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan
public class CalculationLauncher {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(CalculationLauncher.class)) {

            Arrays.stream(context.getBeanDefinitionNames())
                    .forEach(System.out::println);

            System.out.println(context.getBean(BusinessCalculationService.class).findMax()); // 55
        }
    }
}
