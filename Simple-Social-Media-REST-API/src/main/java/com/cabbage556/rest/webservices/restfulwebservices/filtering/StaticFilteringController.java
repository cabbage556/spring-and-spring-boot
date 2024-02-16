package com.cabbage556.rest.webservices.restfulwebservices.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class StaticFilteringController {

    @GetMapping(path = "/static-filtering")
    public StaticFilteringBean filtering() {
        return new StaticFilteringBean("value1", "value2", "value3");
    }

    @GetMapping(path = "/static-filtering-list")
    public List<StaticFilteringBean> filteringList() {
        return Arrays.asList(
                new StaticFilteringBean("value1", "value2", "value3"),
                new StaticFilteringBean("value4", "value5", "value6")
        );
    }
}
