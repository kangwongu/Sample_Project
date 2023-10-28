package com.example.samplesns.mock;

import com.example.samplesns.member.service.port.MyPasswordEncoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FakePasswordEncoder implements MyPasswordEncoder {

    public final String encodeString;

    @Override
    public String encode(String rawPassword) {
        return encodeString + rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return (encodedPassword).equals(encodeString+rawPassword);
    }
}
