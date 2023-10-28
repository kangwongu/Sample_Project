package com.example.samplesns.common.security.entrypoint;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        String errorCode;

        if (exception == null) {
            errorCode = "INVALID JWT TOKEN, JWT 토큰이 유효하지 않습니다.";
            setResponse(response, errorCode);
        } else if (exception.equals("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.")) {
            errorCode = "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
            setResponse(response, errorCode);
        } else if (exception.equals("Expired JWT token, 만료된 JWT token 입니다.")) {
            errorCode = "Expired JWT token, 만료된 JWT token 입니다.";
            setResponse(response, errorCode);
        } else if (exception.equals("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.")) {
            errorCode = "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
            setResponse(response, errorCode);
        } else if (exception.equals("SignatureFailed JWT token, 시그니처 검증에 실패한 JWT 토큰 입니다.")) {
            errorCode = "SignatureFailed JWT token, 시그니처 검증에 실패한 JWT 토큰 입니다.";
            setResponse(response, errorCode);
        } else if (exception.equals("JWT claims is empty, 잘못된 JWT 토큰 입니다.")) {
            errorCode = "JWT claims is empty, 잘못된 JWT 토큰 입니다.";
            setResponse(response, errorCode);
        } else {
            errorCode = "INVALID JWT TOKEN, JWT 토큰이 유효하지 않습니다.";
            setResponse(response, errorCode);
        }

    }

    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorCode);
    }
}
