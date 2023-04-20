package com.example.gtmvcserverside.member.util;

import com.example.gtmvcserverside.member.domain.GTUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
public class GTJwtUtil {

    private static final String ACCESS_TOKEN_PREFIX = "access_token:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String REDIS_KEY_DELIMITER = ":";

    /**
     * 인코딩 알고리즘
     */
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    /**
     * Access Token의 만료 기한을 지정하기 위한 멤버변수입니다.
     */
    @Value("${jwt.access.token.expiration.time.minutes}")
    private Long accessTokenExpirationTimeMinutes;

    /**
     * Refresh Token의 만료 기한을 지정하기 위한 멤버변수입니다.
     */
    @Value("${jwt.refresh.token.expiration.time.minutes}")
    private Long refreshTokenExpirationTimeMinutes;

    /**
     * 서버의 지정된 문자열 시크릿 키를 가지고 HMAC-SHA algorithms을 통해 새로운 Secret Key 를 발급합니다.
     */
    private final Key signingKey;

    @Autowired
    public GTJwtUtil(@Value("${jwt.secret}") String gtSecretKey){
        this.signingKey = Keys.hmacShaKeyFor(gtSecretKey.getBytes());
    }

    /**
     * 새로운 JWT AccessToken을 발급합니다.
     * @param authentication
     * @return
     */
    public String generateAccessToken(Authentication authentication){
        GTUserDetails userPrincipal = (GTUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + accessTokenExpirationTimeMinutes);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(signingKey, algorithm)
                .compact();
    }

    /**
     * TODO : 새로운 JWT Access Token을 발급하면서, 원본 시그니쳐를 Redis에 저장합니다.
     * @param authentication
     * @return
     */
    public String generateSpecialAccessToken(Authentication authentication){
        String newAccessToken = generateAccessToken(authentication);

        // 원본 시그니쳐 저장
        // redisTemplate.opsForValue().set(ACCESS_TOKEN_PREFIX + userDetails.getUserId() + REDIS_KEY_DELIMITER + newAccessToken, newAccessToken);
        return newAccessToken;
    }

    /**
     * JWT Token으로부터 사용자 이름을 얻어오는 메소드
     * @param jwtToken JWT TOKEN
     * @return 사용자 이름
     */
    public String getUserNameFromJwtToken(String jwtToken){
        return Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    /**
     * JWT Token을 검증합니다.
     * @param authToken JWT Token
     * @return 검증 여부
     */
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
