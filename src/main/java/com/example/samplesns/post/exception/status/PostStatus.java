package com.example.samplesns.post.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    NOT_EXIST_POST(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    NOT_VALID_PERMISSION(HttpStatus.FORBIDDEN, "본인이 작성한 게시글에만 접근할 수 있습니다."),
    ALREADY_DELETED_STATE(HttpStatus.BAD_REQUEST, "이미 삭제 처리된 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
