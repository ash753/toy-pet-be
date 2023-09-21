package com.toy.pet.domain.common;


import com.toy.pet.domain.enums.StatusCode;
import lombok.Getter;

@Getter
public class Result {
    private final StatusCode status;
    private final String code;
    private final String message;
    private final Object result;

    public Result(StatusCode status) {
        this.status = status;
        this.code = null;
        this.message = null;
        this.result = null;
    }

    public Result(StatusCode status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public Result(StatusCode status, Object result) {
        this.status = status;
        this.code = null;
        this.message = null;
        this.result = result;
    }
}
