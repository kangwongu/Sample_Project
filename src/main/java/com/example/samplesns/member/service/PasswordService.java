package com.example.samplesns.member.service;

import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.member.service.port.MyPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final MyPasswordEncoder passwordEncoder;

    public String encode(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new MemberException(MemberStatus.NOT_CORRECT_PASSWORD);
        }

        return passwordEncoder.encode(password1);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new MemberException(MemberStatus.NOT_CORRECT_PASSWORD);
        }

        return true;
    }

}
