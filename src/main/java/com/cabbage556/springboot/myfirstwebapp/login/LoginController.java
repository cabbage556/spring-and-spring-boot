package com.cabbage556.springboot.myfirstwebapp.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("login")
    public String login(
            @RequestParam String name, // name 요청 쿼리 파라미터
            ModelMap model // 컨트롤러에서 뷰에 뭔가를 제공하기 위해 사용
    ) {
        // JSP에 name 요청 쿼리 파라미터의 값 제공하기
        model.put("name", name); // JSP: ${name}

        // debug 수준 로깅
        logger.debug("Request param : {}", name);

        // 스프링 MVC ViewResolver
        // 스프링 부트 JSP 기본 폴더 + prefix + 메서드 리턴 + suffix => 리다이렉션
        return "login";
    }
}
