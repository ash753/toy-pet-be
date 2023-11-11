package com.toy.pet.domain.enums;

import com.toy.pet.exception.CommonException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Getter
public enum ResponseCode {
    CODE_0001("0001", "system error"),
    CODE_0002("0002", "잘못된 enum code입니다."),
    CODE_0003("0003", "Bean Validation 오류"),
    CODE_0004("0004", "읽을 수 없는 HTTP 요청"),

    // 인증, 인가
    CODE_0010("0010", "인증 실패"),
    CODE_0011("0011", "잘못된 JWT 서명입니다."),
    CODE_0012("0012", "JWT 토큰이 잘못되었습니다."),
    CODE_0013("0013", "JWT 토큰이 만료되었습니다."),
    CODE_0014("0014", "유효하지 않은 Provider Access Token입니다. 토큰을 확인해주세요."),
    CODE_0015("0015", "이미 가입된 회원입니다."),
    CODE_0016("0016", "존재하지 않는 회원입니다"),
    CODE_0017("0017", "아직 회원가입을 하지 않은 회원입니다. 회원가입을 진행해주세요"),
    CODE_0018("0018", "올바르지 않은 이메일 형식입니다."),

    // 파일 관련
    CODE_0040("0040", "첨부파일 업로드에 실패했습니다"),
    CODE_0041("0041", "이미지 파일만 저장할 수 있습니다."),

    // 약관 관련
    CODE_0060("0060", "올바르지 않은 약관 id 목록입니다."),
    CODE_0061("0061", "필수 약관에 대해 모두 동의해주세요."),


    CODE_0080("0080", "공유 코드에 대한 반려 동물이 존재하지 않습니다."),
    CODE_0081("0081", "공유 코드가 없다면 반려동물 등록 정보가 있어야 합니다."),
    CODE_0082("0082", "공유 코드로 반려동물을 연결하는 경우 반려동물 프로필 이미지를 변경할 수 없습니다."),
    ;


    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCode findByCode(String code){
        if (ObjectUtils.isEmpty(code)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
        }

        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.code.equals(code)) {
                return responseCode;
            }
        }

        throw new CommonException(HttpStatus.BAD_REQUEST, ResponseCode.CODE_0002);
    }
}
