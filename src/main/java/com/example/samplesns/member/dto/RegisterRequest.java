package com.example.samplesns.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Schema(description = "회원가입 포맷")
@Getter
public class RegisterRequest {
    @Email
    @NotBlank
    private String email;

    // 영문, 숫자, 특수문자 포함한 8~20 자리
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$")
    @NotBlank
    @Schema(description = "패스워드")
    private String password1;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$")
    @NotBlank
    @Schema(description = "패스워드 확인")
    private String password2;

    @Size(max = 10)
    @NotBlank
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private LocalDate birthday;
}
