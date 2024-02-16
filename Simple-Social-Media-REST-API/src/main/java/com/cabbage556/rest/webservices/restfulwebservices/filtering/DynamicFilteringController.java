package com.cabbage556.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class DynamicFilteringController {

    @GetMapping(path = "/dynamic-filtering")
    public MappingJacksonValue filtering() {
        // 동적 필터링 방식 정의: MappingJacksonValue 사용
        //      Jackson 컨버터에 특정 직렬화 명령을 전달하고 싶은 경우 사용
        MappingJacksonValue mappingJacksonValue =
                new MappingJacksonValue(new DynamicFilteringBean("value1", "value2", "value3"));
        // 필터 생성
        //      DynamicFilteringBean의 field1, field3을 제외한 프로퍼티는 응답에서 필터링(제거)
        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
        // "DynamicFilteringBeanFilter"라는 이름으로 필터 추가
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("DynamicFilteringBeanFilter", filter);
        // 필터 적용
        mappingJacksonValue.setFilters(filters);

        // 직렬화 결과 field2을 제외한 응답 JSON 확인 가능
        return mappingJacksonValue;
    }

    @GetMapping(path = "/dynamic-filtering-list")
    public MappingJacksonValue filteringList() {
        MappingJacksonValue mappingJacksonValue =
                new MappingJacksonValue(
                        Arrays.asList(
                                new DynamicFilteringBean("value1", "value2", "value3"),
                                new DynamicFilteringBean("value4", "value5", "value6")
                        )
                );
        SimpleBeanPropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("DynamicFilteringBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);

        // 직렬화 결과 field1을 제외한 응답 JSON 확인 가능
        return mappingJacksonValue;
    }
}
