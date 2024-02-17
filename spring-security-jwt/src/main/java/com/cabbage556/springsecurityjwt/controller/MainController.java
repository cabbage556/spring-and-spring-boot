package com.cabbage556.springsecurityjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody  // 데이터 응답 컨트롤러
public class MainController {

    @GetMapping("/")
    public String mainP() {
        return "main controller";
    }
}
