package com.example.gtmvcserverside.member.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class GTJwtUtil {

    @Value("${jwt.secret}")
    private String gtSecretKey;

    private static final String ACCESS_TOKEN_PREFIX = "access_token:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String REDIS_KEY_DELIMITER = ":";
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.access.token.expiration.time.minutes}")
    private Long accessTokenExpirationTimeMinutes;

    @Value("${jwt.refresh.token.expiration.time.minutes}")
    private Long refreshTokenExpirationTimeMinutes;

    private final Key signingKey;

    @Autowired
    public GTJwtUtil(@Value("${jwt.secret}") String gtSecretKey){
        this.signingKey = Keys.hmacShaKeyFor(gtSecretKey.getBytes());
    }


    public String generateAccessToken(){
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + accessTokenExpirationTimeMinutes);

        String newAccessToken = Jwts.builder()
                // .setSubject()
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(signingKey, algorithm)
                .compact();

        return newAccessToken;
    }

    public String generateSpecialAccessToken(){
        String newAccessToken = generateAccessToken();

        // 원본 시그니쳐 저장
        // redisTemplate.opsForValue().set(ACCESS_TOKEN_PREFIX + userDetails.getUserId() + REDIS_KEY_DELIMITER + newAccessToken, newAccessToken);
        return newAccessToken;
    }

}
