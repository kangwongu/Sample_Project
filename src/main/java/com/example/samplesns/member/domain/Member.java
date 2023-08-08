package com.example.samplesns.member.domain;

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
    private final String nickname;
    private final LocalDate birthday;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;

    @Builder
    public Member(Long id, String email, String nickname, LocalDate birthday, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.email = Objects.requireNonNull(email);

        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.birthday = Objects.requireNonNull(birthday);
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= 10, "닉네임은 10자를 초과할 수 없어요");
    }

}
