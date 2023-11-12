package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Schema
@Getter
public enum EnumerationCategory {
    DOG_BREED("DOG_BREED", "강아지 품종"),
    CAT_BREED("CAT_BREED", "고양이 품종"),
    ;

    private String code;
    private String name;

    EnumerationCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static EnumerationCategory findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
        }

        for (EnumerationCategory enumerationCategory : EnumerationCategory.values()) {
            if (enumerationCategory.code.equals(code)) {
                return enumerationCategory;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
    }
}
