package com.example.samplesns.member.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {

    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    DUPLICATED_MEMBER(HttpStatus.CONFLICT, "중복된 회원이 존재합니다."),
    NOT_CORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
