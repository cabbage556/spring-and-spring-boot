package com.cabbage556.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("field1")  // 응답에서 필드를 제외하는 어노테이션(정적 필터링): 클래스에 적용, 프로퍼티 이름 명시
public class StaticFilteringBean {
    private String field1;

    @JsonIgnore  // 응답에서 해당 필드를 제외하는 어노테이션(정적 필터링): 멤버 변수에 적용
    private String field2;
    private String field3;

    public StaticFilteringBean(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                '}';
    }
}
