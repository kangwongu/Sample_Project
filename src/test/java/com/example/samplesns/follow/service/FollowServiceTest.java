package com.example.samplesns.follow.service;

import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.mock.TestContainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FollowServiceTest {

    @Test
    public void 회원은_특정_회원을_팔로우할_수_있다() {
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

        FollowRequest request = new FollowRequest();
        request.setEmail("kwg0527@naver.com");

        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(Member.builder()
                .id(2L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("5t6y7u8i")
                .status(MemberStatus.ACTIVE)
                .build());

        // when
        testContainer.followService.follow(fromMember, request);

        // then
        assertThat(testContainer.followRepository.findById(1L).isPresent()).isTrue();
        assertThat(testContainer.followRepository.findById(1L).get().getFromMember().getEmail()).isEqualTo("kwg2358@gmail.com");
        assertThat(testContainer.followRepository.findById(1L).get().getToMember().getEmail()).isEqualTo("kwg0527@naver.com");
    }

}