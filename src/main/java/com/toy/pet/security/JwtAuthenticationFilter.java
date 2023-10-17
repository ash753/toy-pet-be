package com.toy.pet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.pet.domain.common.PrincipalDetails;
import com.toy.pet.domain.common.Result;
import com.toy.pet.domain.common.User;
import com.toy.pet.domain.entity.Member;
import com.toy.pet.domain.enums.StatusCode;
import com.toy.pet.exception.CommonException;
import com.toy.pet.service.auth.JwtTokenService;
import com.toy.pet.service.member.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Order(0)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final MemberService memberService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            if (StringUtils.hasText(token)) {
                Long memberId = jwtTokenService.getMemberId(token);
                Member member = memberService.getMember(memberId);
                User user = new User(member);
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CommonException e) { // 필터 단 커스텀 에러 처리
            log.error("", e);
            Result result = new Result(StatusCode.FAILURE, e.getCode(), e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(token -> token.substring(7))
                .orElse(null);
    }
}
