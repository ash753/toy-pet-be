package com.toy.pet.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    /**
     * "Content-Type: multipart/form-data" 헤더를 지원하는 HTTP 요청 변환기
     * https://velog.io/@zvyg1023/Spring-Boot-Swagger%EC%97%90%EC%84%9C-ReqeustPart%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-MultiPartFile%EA%B3%BC-DTO-%EC%B2%98%EB%A6%AC-%EC%8B%9C-Content-type-applicationoctet-stream-not-supported-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0
     */
    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
