package com.toy.pet.exception;

import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public Result handleCommonException(CommonException e) {
        log.error("", e);
        return new Result(StatusCode.FAILURE, e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handelException(Exception e) {
        log.error("", e);
        return new Result(StatusCode.FAILURE, ResponseCode.CODE_0001.getCode(), ResponseCode.CODE_0001.getMessage());
    }
}
