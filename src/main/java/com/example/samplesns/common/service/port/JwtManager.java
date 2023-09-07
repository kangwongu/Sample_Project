package com.example.samplesns.common.service.port;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtManager {
    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    String generateAccessToken(String email);
    String resolveToken(HttpServletRequest request);
    Boolean validateToken(String token);
    Authentication createAuthentication(String token);
}
