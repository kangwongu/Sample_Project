package com.example.samplesns.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "회원가입 포맷")
@Getter
public class RegisterRequest {
    private String email;

    @Schema(description = "패스워드")
    private String password1;

    @Schema(description = "패스워드 확인")
    private String password2;

    private String nickname;

    private LocalDate birthday;
}
