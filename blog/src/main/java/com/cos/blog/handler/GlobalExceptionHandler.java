package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 모든 Exception이 발생하면 여기로 들어오게 됨.
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) // value에 해당하는 exception이 발생하면 해당 에러를 이 함수에다가 전달함
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>" + e.getMessage() + "</h1>";
    }
}
