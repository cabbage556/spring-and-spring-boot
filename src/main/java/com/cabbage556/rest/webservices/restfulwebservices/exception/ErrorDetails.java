package com.cabbage556.rest.webservices.restfulwebservices.exception;

import java.time.LocalDateTime;

// 커스텀 에러 객체
//      예외 발생 시 커스텀 에러 객체 구조 반환
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, String mesage, String details) {
        this.timestamp = timestamp;
        this.message = mesage;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
