package com.example.samplesns.follow.service;

import com.example.samplesns.follow.domain.Follow;
import com.example.samplesns.follow.dto.FollowRequest;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.member.dto.MemberResponse;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.mock.TestContainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FollowServiceTest {

    @Test
    public void ACTIVE_상태의_회원은_특정_회원을_팔로우할_수_있다() {
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

    @Test
    public void PENDING_상태의_회원은_특정_회원을_팔로우할_수_없다() {
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
                .status(MemberStatus.PENDING)
                .build());

        // when
        // then
        assertThatThrownBy(() -> testContainer.followService.follow(fromMember, request)
                ).isInstanceOf(MemberException.class);
    }

    @Test
    public void 로그인한_회원은_자신을_팔로우중인_회원들의_정보를_볼_수_있다() {
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

        Member toMember1 = Member.builder()
                .id(2L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1900, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        Member toMember2 = Member.builder()
                .id(3L)
                .email("kwg0527@aimeelabs.com")
                .password("1q2w3e4r!@#$")
                .nickname("29")
                .birthday(LocalDate.of(1950, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(fromMember);
        testContainer.memberRepository.save(toMember1);
        testContainer.memberRepository.save(toMember2);

        testContainer.followRepository.save(Follow.builder()
                        .id(1L)
                        .fromMember(fromMember)
                        .toMember(toMember1)
                        .build()
        );
        testContainer.followRepository.save(Follow.builder()
                        .id(2L)
                        .fromMember(fromMember)
                        .toMember(toMember2)
                        .build()
        );

        // when
        List<MemberResponse> followingMembers = testContainer.followService.getFollowingMembers(fromMember);
        
        // then
        assertThat(followingMembers).isNotNull();
        assertThat(followingMembers.size()).isEqualTo(2);
        assertThat(followingMembers.get(0).getEmail()).isEqualTo("kwg0527@naver.com");
        assertThat(followingMembers.get(1).getEmail()).isEqualTo("kwg0527@aimeelabs.com");
    }

    @Test
    public void 로그인한_회원을_팔로우중인_회원이_없으면_빈_리스트를_반환한다() {
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

        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(fromMember);

        // when
        List<MemberResponse> followingMembers = testContainer.followService.getFollowingMembers(fromMember);

        // then
        assertThat(followingMembers).isNotNull();
        assertThat(followingMembers.size()).isEqualTo(0);
    }
}