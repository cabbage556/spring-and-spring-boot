package com.liberty556.learnspringframework.xmlConfigurationContext;

import com.liberty556.learnspringframework.game.GameRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class XmlConfigurationContextLauncher {

    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("contextConfiguration.xml")) {
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

            // System.out.println(context.getBean("name")); // Cabbage
            // System.out.println(context.getBean("age")); // 30

            context.getBean(GameRunner.class).run();
        }
    }
}
