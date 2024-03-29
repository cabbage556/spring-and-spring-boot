package com.cabbage556.rest.webservices.restfulwebservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController  // REST API 컨트롤러
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        // HelloWorldBean -> JSON
        //      @ResponseBody + JacksonHttpMessageConverters
        //          @ResponseBody: @RestController 어노테이션 내부에 선언된 어노테이션, 스프링 빈을 있는 그대로 반환하게 함
        //          JacksonHttpMessageConverters: 스프링 부트 Auto Configuration이 설정한 기본 변환, 스프링 빈을 JSON으로 변환
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    // i18n 메서드
    @GetMapping(path = "/hello-world-i18n")
    public String helloWorldi18n() {
        // LocaleContextHolder.getLocale() 유틸리티 메서드로 로케일 가져오기
        //      Accept-Language 헤더의 값에 대한 로케일을 가져옴
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale.getDisplayLanguage());
        return messageSource
                .getMessage("good.morning.message", null, "Default Message", locale);
    }
}
