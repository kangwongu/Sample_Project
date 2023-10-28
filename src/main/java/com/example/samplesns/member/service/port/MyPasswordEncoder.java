package com.example.samplesns.member.service.port;

public interface MyPasswordEncoder {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
