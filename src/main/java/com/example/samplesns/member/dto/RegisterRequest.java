package com.example.samplesns.member.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterRequest {

    private String email;
    private String password1;
    private String password2;
    private String nickname;
    private LocalDate birthday;
}
