package com.toy.pet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.StatusCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("", authException);
        Result result = new Result(StatusCode.FAILURE, ResponseCode.CODE_0010.getCode(),
                ResponseCode.CODE_0010.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
