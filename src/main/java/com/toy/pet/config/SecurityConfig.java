package com.toy.pet.config;

import com.toy.pet.security.JwtAuthenticationEntryPoint;
import com.toy.pet.security.JwtAuthenticationFilter;
import com.toy.pet.service.auth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2UserService oauth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // 참고 https://colabear754.tistory.com/171
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector handlerMappingIntrospector) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(securitySessionManagementConfigurer -> {
                    securitySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/swagger-ui/**")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/v3/**")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/login")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/auth/login/**")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/auth/reissue")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/members/oauth-info")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/members/register/**")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/terms/member-register")).permitAll()
                            .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/enumerations")).permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oauth2UserService))
                )
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                })
                .build();
    }
}
