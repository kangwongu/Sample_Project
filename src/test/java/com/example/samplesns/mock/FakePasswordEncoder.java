package com.example.samplesns.mock;

import com.example.samplesns.member.service.port.MyPasswordEncoder;

public class FakePasswordEncoder implements MyPasswordEncoder {

    public String password;

    @Override
    public String encode(String rawPassword) {
        password = "encoded! " + rawPassword;
        return password;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return false;
    }
}
