package com.toy.pet.exception;

import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Result> handleCommonException(CommonException e) {
        log.error("", e);
        Result result = new Result(StatusCode.FAILURE, e.getCode(), e.getMessage());
        return new ResponseEntity<>(result, e.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleConstraintViolationException(MethodArgumentNotValidException e){
        log.error("", e);
        Result result = new Result(StatusCode.FAILURE, ResponseCode.CODE_0002.getCode(), e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("", e);
        Result result = new Result(StatusCode.FAILURE, ResponseCode.CODE_0004.getCode(), e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handelException(Exception e) {
        log.error("", e);
        Result result = new Result(StatusCode.FAILURE, ResponseCode.CODE_0001.getCode(), ResponseCode.CODE_0001.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
