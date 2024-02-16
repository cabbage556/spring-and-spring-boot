package com.cabbage556.rest.webservices.restfulwebservices.exception;

import com.cabbage556.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice  // 이 클래스를 모든 컨트롤러에 적용하는 어노테이션
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)  // 어떤 예외를 처리할지 정의하는 어노테이션: 모든 예외를 처리함
    public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
        // 커스텀 예외 객체 생성
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        // 커스텀 예외 객체 응답 + 500 Internal Server Error 상태 코드 설정
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)  // UserNotFoundException 예외를 처리함
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        // 커스텀 예외 객체 응답 + 404 Not Found 상태 코드 설정
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // @Valid 검증이 실패하는 경우 던져지는 예외를 처리하는 메서드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = "Total Errors: " + ex.getErrorCount()
                + ", First Error: " + ex.getFieldError().getDefaultMessage();  // 에러 메세지 생성
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false));

        // 커스텀 예외 객체 응답 + 400 Bad Request 상태 코드 설정
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
