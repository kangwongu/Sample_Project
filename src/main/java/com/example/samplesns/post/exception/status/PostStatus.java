package com.example.samplesns.post.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    NOT_EXIST_POST(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
