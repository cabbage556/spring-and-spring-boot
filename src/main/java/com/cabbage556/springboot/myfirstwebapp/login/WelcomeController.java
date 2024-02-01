package com.cabbage556.springboot.myfirstwebapp.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")  // 모델에 추가한 name 데이터를 세션에 넣기 위해 사용하는 어노테이션
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String gotoWelcomePage(ModelMap model) {
        model.put("name", getLoggedInUsername());

        // 스프링 MVC ViewResolver: 스프링 부트 JSP 기본 폴더 + prefix + 메서드 리턴 + suffix => 리다이렉션
        return "welcome";
    }

    private String getLoggedInUsername() {
        // 현재 인증된 주체를 가져와 username 리턴
        //      인증된 유저가 있다면 인증된 유저의 username 리턴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
