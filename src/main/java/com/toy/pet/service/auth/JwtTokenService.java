package com.toy.pet.service.auth;

import com.toy.pet.domain.common.User;
import com.toy.pet.domain.enums.ResponseCode;
import com.toy.pet.domain.enums.Role;
import com.toy.pet.exception.CommonException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class JwtTokenService implements InitializingBean {
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${jwt.expire-hour.access-token}")
    private Integer accessTokenExpireHour;
    @Value("${jwt.expire-hour.refresh-token}")
    private Integer refreshTokenExpireHour;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(User user) {

        long now = (new Date()).getTime();
        Date validDate = new Date(now + accessTokenExpireHour * 60L * 60 * 1000);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("authorities", user.getRoleList().stream()
                        .map(role -> role.getCode())
                        .collect(Collectors.joining(",")))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validDate)
                .compact();
    }

    public String reIssueAccessToken(String refreshToken){
        User user = getUserInfo(refreshToken);
        return createAccessToken(user);
    }

    public String createRefreshToken(User user) {

        long now = (new Date()).getTime();
        Date validDate = new Date(now + refreshTokenExpireHour * 60L * 60 * 1000);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("authorities", user.getRoleList().stream()
                        .map(role -> role.getCode())
                        .collect(Collectors.joining(",")))
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validDate)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CommonException(ResponseCode.CODE_0011);
        } catch (IllegalArgumentException | UnsupportedJwtException e) {
            throw new CommonException(ResponseCode.CODE_0012);
        } catch (ExpiredJwtException e) {
            throw new CommonException(ResponseCode.CODE_0013);
        }
    }

    public User getUserInfo(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<Role> authoritieList = Arrays.stream(claims.get("authorities").toString().split(","))
                .map(code -> {
                    return Role.findByCode(code);
                }).collect(Collectors.toList());
        return new User(Long.valueOf(claims.get("id").toString()), claims.get("name").toString(), authoritieList);
    }
}
