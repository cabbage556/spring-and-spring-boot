package com.cabbage556.springboot.myfirstwebapp.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String goToLoginPage() {
        // 스프링 MVC ViewResolver
        // 스프링 부트 JSP 기본 폴더 + prefix + 메서드 리턴 + suffix => 리다이렉션
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String goToWelcomePage(
            @RequestParam String name,
            @RequestParam String password,
            ModelMap model
    ) {
        if (!authenticationService.authenticate(name, password)) {
            model.put("errorMessage", "Invalid Credentials! Please try agian.");
            return "login";
        }

        model.put("name", name);
        return "welcome";
    }
}
