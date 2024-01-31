package com.cabbage556.springboot.myfirstwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  // 요청 처리를 담당하는 컨트롤러 역할의 스프링 빈 어노테이션
public class SayHelloController {

    @RequestMapping("say-hello")
    @ResponseBody  // 메서드의 리턴 값을 그대로 브라우저에 리턴
    public String sayHello() {
        return "Hello! What are you learning today?";
    }

    @RequestMapping("say-hello-html")
    @ResponseBody
    public String sayHelloHtml() {
        // 문자열 HTML 그대로 브라우저에 리턴
        //      복잡한 방식
        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title> My First HTML Page </title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("My First HTML page with body");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }

    // JSP(Java Server Page)
    //      say-hello-jsp -> /src/main/resources/META-INF/resources/WEB-INF/jsp/sayHello.jsp (리다이렉트)
    //          /src/main/resources/META-INF/resources : 스프링 부트 JSP 기본 폴더
    //          /WEB-INF/jsp/ : application.properties prefix 설정
    //          .jsp : application.properties suffix 설정
    @RequestMapping("say-hello-jsp")
    public String sayHelloJsp() {
        // JSP 파일 이름 리턴
        //      스프링 부트 JSP 기본 폴더 + prefix + 메서드 리턴(JSP 파일 이름) + suffix
        return "sayHello";
    }

}
