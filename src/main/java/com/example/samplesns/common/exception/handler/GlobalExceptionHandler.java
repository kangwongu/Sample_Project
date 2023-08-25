package com.example.samplesns.common.exception.handler;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MemberException.class})
    public ResponseEntity<Object> memberException(MemberException e) {
        MemberStatus status = e.getStatus();
        ExceptionResponse response = new ExceptionResponse(status.getHttpStatus(), status.getMessage());
        return ResponseEntity.status(status.getHttpStatus()).body(response);
    }
}
