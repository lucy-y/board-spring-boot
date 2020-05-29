package com.lucy.board.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucy.board.config.domain.ErrorCode;
import com.lucy.board.config.domain.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public Result<Void> handleCustomException(CustomException e) {
        log.error("[CustomException] Handle");
        e.printStackTrace();
        return new Result<>(e.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public Result<Void> handleException(Exception e) {
        log.error("[Exception] Handle");
        e.printStackTrace();
        return new Result<>(ErrorCode.UNKNOWNERROR);
    }
}
