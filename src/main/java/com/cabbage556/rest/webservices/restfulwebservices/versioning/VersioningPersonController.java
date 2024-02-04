package com.cabbage556.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    // REST API 버저닝: URL
    @GetMapping(path = "/v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Bob Charlie");
    }

    // REST API 버저닝: URL
    @GetMapping(path = "/v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // REST API 버저닝: 요청 파라미터
    //      /person?version=1
    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParam() {
        return new PersonV1("Bob Charlie");
    }

    // REST API 버저닝: 요청 파라미터
    //      /person?version=2
    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParam() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // REST API 버저닝: 요청 커스텀 헤더
    //      커스텀 요청 헤더 설정 X-API-VERSION=1
    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader() {
        return new PersonV1("Bob Charlie");
    }

    // REST API 버저닝: 요청 커스텀 헤더
    //      커스텀 요청 헤더 설정 X-API-VERSION=2
    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // REST API 버저닝: 요청 Accept 헤더
    //      요청 Accept 헤더 설정 Accept=application/vnd.company.app-v1.json
    //      Accept 헤더의 값을 얻기 위해 produces 사용
    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader() {
        return new PersonV1("Bob Charlie");
    }

    // REST API 버저닝: 요청 Accept 헤더
    //      요청 Accept 헤더 설정 Accept=application/vnd.company.app-v2.json
    //      Accept 헤더의 값을 얻기 위해 produces 사용
    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeader() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }
}
