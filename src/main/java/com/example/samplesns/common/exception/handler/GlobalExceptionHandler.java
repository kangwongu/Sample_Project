package com.example.samplesns.common.exception.handler;

import com.example.samplesns.common.exception.response.ExceptionResponse;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getFieldError().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({MemberException.class})
    public ResponseEntity<Object> memberException(MemberException e) {
        MemberStatus status = e.getStatus();
        ExceptionResponse response = new ExceptionResponse(status.getHttpStatus(), status.getMessage());
        return ResponseEntity.status(status.getHttpStatus()).body(response);
    }

    @ExceptionHandler({PostException.class})
    public ResponseEntity<Object> postException(PostException e) {
        PostStatus status = e.getStatus();
        ExceptionResponse response = new ExceptionResponse(status.getHttpStatus(), status.getMessage());
        return ResponseEntity.status(status.getHttpStatus()).body(response);
    }
}
