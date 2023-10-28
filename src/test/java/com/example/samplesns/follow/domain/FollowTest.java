package com.example.samplesns.follow.domain;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class FollowTest {

    @Test
    public void fromMember와_toMember로_Follow_객체를_생성할_수_있다() {
        // given
        Member fromMember = Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        Member toMember = Member.builder()
                .id(2L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("5t6y7u8i")
                .status(MemberStatus.ACTIVE)
                .build();

        // when
        Follow follow = Follow.of(fromMember, toMember);

        // then
        assertThat(follow.getId()).isNull();
        assertThat(follow.getFromMember().getEmail()).isEqualTo("kwg2358@gmail.com");
        assertThat(follow.getToMember().getEmail()).isEqualTo("kwg0527@naver.com");
    }

}