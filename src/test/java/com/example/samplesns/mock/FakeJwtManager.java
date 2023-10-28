package com.example.samplesns.mock;

import com.example.samplesns.common.service.port.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class FakeJwtManager implements JwtManager {

    public final String token;

    @Override
    public String generateAccessToken(String email) {
        return TOKEN_PREFIX + email + token;
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return null;
    }

    @Override
    public Boolean validateToken(String token) {
        return null;
    }

    @Override
    public Authentication createAuthentication(String token) {
        return null;
    }
}
