package com.toy.pet.domain.common;

import lombok.Getter;

@Getter
public class Constant {
    public static final String BEARER = "Bearer ";

    public static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
}
