package com.cabbage556.springboot.learnspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController  // REST API 컨트롤러
public class CurrencyConfigurationController {

    @Autowired
    private CurrencyServiceConfiguration configuration;

    // GET /currency-configuration
    @RequestMapping("/currency-configuration")
    public CurrencyServiceConfiguration retrieveCurrencyServiceConfiguration() {
        return configuration;
    }
}
