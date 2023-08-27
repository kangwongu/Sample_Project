package com.example.samplesns.member.exception;


import com.example.samplesns.member.exception.status.MemberStatus;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final MemberStatus status;

    public MemberException(MemberStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
