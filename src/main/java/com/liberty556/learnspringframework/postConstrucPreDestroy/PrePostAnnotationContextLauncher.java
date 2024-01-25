package com.liberty556.learnspringframework.postConstrucPreDestroy;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
class SomeClass {
    private SomeDependency someDependency;

    public SomeClass(SomeDependency someDependency) {
        this.someDependency = someDependency;
        System.out.println("All dependencies are ready!");
    }

    // 모든 의존성이 연결된 후 스프링은 @PostConstruct 어노테이션 메서드를 호출함
    //      의존성이 연결되는 대로 어떤 초기화 로직을 실행하려는 경우 사용
    @PostConstruct
    public void initialize() {
        someDependency.getReady();
    }

    // 스프링 컨테이너에서 인스턴스를 삭제하는 과정 중에 있음 알려주는 콜백 알림(보유한 리소스를 해제하는 데 일반적으로 사용됨)
    //      컨테이너에서 빈이 삭제되기 전에 cleanup을 수행하려는 경우 사용
    @PreDestroy
    public void cleanup() {
        System.out.println("Cleanup");
    }
}

@Component
class SomeDependency {
    public void getReady() {
        System.out.println("Some logic using SomeDependency");
    }
}

@Configuration
@ComponentScan
public class PrePostAnnotationContextLauncher {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(PrePostAnnotationContextLauncher.class)) {
        }
    }
}
