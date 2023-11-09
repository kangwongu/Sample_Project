package com.example.samplesns.member.controller.port;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.LoginRequest;
import com.example.samplesns.member.dto.RegisterRequest;
import org.springframework.http.HttpHeaders;

public interface MemberService {

    Member register(RegisterRequest request);
    void verifyEmail(long memberId, String certificationCode);
    HttpHeaders login(LoginRequest request);
}
