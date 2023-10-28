package com.example.samplesns.common.infrastructure.jwt;

import com.example.samplesns.common.security.userdetails.UserDetailsServiceImpl;
import com.example.samplesns.common.service.port.JwtManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class SystemJwtManager implements JwtManager {

    public final int ACCESS_EXPIRATION_TIME;
    public final BigInteger REFRESH_EXPIRATION_TIME;
    private final String SECRET_KEY;
    private Key key;
    private final UserDetailsServiceImpl userDetailsService;

    public SystemJwtManager(@Value("${spring.jwt.access-expiration-time}") int ACCESS_EXPIRATION_TIME,
                            @Value("${spring.jwt.refresh-expiration-time}") BigInteger REFRESH_EXPIRATION_TIME,
                            @Value("${spring.jwt.secret-key}") String SECRET_KEY,
                            UserDetailsServiceImpl userDetailsService) {
        this.ACCESS_EXPIRATION_TIME = ACCESS_EXPIRATION_TIME * 60;
        this.REFRESH_EXPIRATION_TIME = REFRESH_EXPIRATION_TIME;
        this.SECRET_KEY = SECRET_KEY;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        byte[] decodeSecretKey = Base64.getDecoder().decode(SECRET_KEY);
        key = Keys.hmacShaKeyFor(decodeSecretKey);
    }

    // AccessToken 생성
    @Override
    public String generateAccessToken(String email) {
        return TOKEN_PREFIX + Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // AccessToken 정보 추출
    @Override
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            log.info("token = {}", token.substring(7));
            return token.substring(7);
        }
        return null;
    }

    // 토큰 검증
    @Override
    public Boolean validateToken(String token) {
        return !Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().isEmpty();
    }

    @Override
    public Authentication createAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }

    public String getUserEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }
}
