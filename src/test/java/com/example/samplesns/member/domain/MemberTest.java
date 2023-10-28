package com.example.samplesns.member.domain;

import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.exception.MemberException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    public void Member는_RegisterRequest_encodedPassword_certificationCode로_생성_할_수_있다() {
        // given
        RegisterRequest request = new RegisterRequest();
        request.setEmail("kwg0527@naver.com");
        request.setNickname("19");
        request.setBirthday(LocalDate.of(1800, 10, 10));

        String encodedPassword = "encodedPassword";
        String certificationCode = "certificationCode";

        // when
        Member member = Member.of(request, encodedPassword, certificationCode);

        // then
        assertThat(member.getEmail()).isEqualTo("kwg0527@naver.com");
        assertThat(member.getNickname()).isEqualTo("19");
        assertThat(member.getPassword()).isEqualTo(encodedPassword);
        assertThat(member.getBirthday()).isEqualTo(LocalDate.of(1800, 10, 10));
        assertThat(member.getCertificationCode()).isEqualTo(certificationCode);

    }

    @Test
    public void Member는_유효한_인증_코드로_계정을_활성화할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 10, 10))
                .certificationCode("1q2w3e4r!@#")
                .status(MemberStatus.PENDING)
                .build();

        // when
        member = member.verify("1q2w3e4r!@#");

        // then
        assertThat(member.getCertificationCode()).isEqualTo("1q2w3e4r!@#");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    public void Member는_유효하지_않은_인증_코드로_인증을_시도하면_에러가_발생한다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 10, 10))
                .certificationCode("1q2w3e4r!@#")
                .status(MemberStatus.PENDING)
                .build();

        // when
        // then
        assertThatThrownBy(() -> member.verify("1q2w3"))
                .isInstanceOf(MemberException.class);
    }
}