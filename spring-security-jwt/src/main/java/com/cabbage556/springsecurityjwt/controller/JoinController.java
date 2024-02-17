package com.cabbage556.springsecurityjwt.controller;

import com.cabbage556.springsecurityjwt.dto.JoinDTO;
import com.cabbage556.springsecurityjwt.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
