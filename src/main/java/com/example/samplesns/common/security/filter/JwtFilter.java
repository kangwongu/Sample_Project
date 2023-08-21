package com.example.samplesns.common.security.filter;

import com.example.samplesns.common.jwt.JwtManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtManager.resolveToken(request);

        try {
            if (token != null && jwtManager.validateToken(token)) {
                Authentication authentication = jwtManager.createAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException | SecurityException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            request.setAttribute("exception", "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            request.setAttribute("exception", "Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            request.setAttribute("exception", "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (SignatureException e) {
            log.error("SignatureFailed JWT token, 시그니처 검증에 실패한 JWT 토큰 입니다.");
            request.setAttribute("exception", "SignatureFailed JWT token, 시그니처 검증에 실패한 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            request.setAttribute("exception", "JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }

        filterChain.doFilter(request, response);
    }
}
