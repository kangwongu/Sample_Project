package com.example.samplesns.member.domain;

import com.example.samplesns.member.dto.RegisterRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDate birthday;
    private final String certificationCode;
    private final MemberStatus status;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;

    @Builder
    public Member(Long id, String email, String password, String nickname, LocalDate birthday, String certificationCode,
                  MemberStatus status, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.birthday = Objects.requireNonNull(birthday);
        this.certificationCode = certificationCode;
        this.status = status;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= 10, "닉네임은 10자를 초과할 수 없어요");
    }

    public static Member of(RegisterRequest request, String encodedPassword, String certificationCode) {
        return Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .birthday(request.getBirthday())
                .certificationCode(certificationCode)
                .status(MemberStatus.PENDING)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
    }
}
