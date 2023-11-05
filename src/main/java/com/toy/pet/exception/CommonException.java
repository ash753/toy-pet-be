package com.toy.pet.exception;

import com.toy.pet.domain.enums.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CommonException extends ResponseStatusException {
    private final String code;
    private final String message;

    public CommonException(HttpStatusCode status, ResponseCode responseCode) {
        super(status);
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public CommonException(HttpStatusCode status, ResponseCode responseCode, Throwable cause) {
        super(status, responseCode.getMessage(), cause);
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public CommonException(HttpStatusCode status, String code, String message) {
        super(status);
        this.code = code;
        this.message = message;
    }

    public CommonException(HttpStatusCode status, String reason, String code, String message) {
        super(status, reason);
        this.code = code;
        this.message = message;
    }

    public CommonException(int rawStatusCode, String reason, Throwable cause, String code, String message) {
        super(rawStatusCode, reason, cause);
        this.code = code;
        this.message = message;
    }

    public CommonException(HttpStatusCode status, String reason, Throwable cause, String code, String message) {
        super(status, reason, cause);
        this.code = code;
        this.message = message;
    }

    public CommonException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode, Object[] messageDetailArguments, String code, String message) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
        this.code = code;
        this.message = message;
    }
}
