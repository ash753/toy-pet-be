package com.toy.pet.exception;

import com.toy.pet.domain.enums.ResponseCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final String code;
    private final String message;

    public CommonException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public CommonException(String message, String code, String message1) {
        super(message);
        this.code = code;
        this.message = message1;
    }

    public CommonException(String message, Throwable cause, String code, String message1) {
        super(message, cause);
        this.code = code;
        this.message = message1;
    }

    public CommonException(Throwable cause, String code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public CommonException(Throwable cause, ResponseCode responseCode) {
        super(cause);
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message1;
    }
}
