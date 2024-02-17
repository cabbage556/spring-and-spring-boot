package com.cabbage556.springsecurityjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody  // 데이터 응답 컨트롤러
public class AdminController {

    @GetMapping("/admin")
    public String adminP() {
        return "admin controller";
    }
}
