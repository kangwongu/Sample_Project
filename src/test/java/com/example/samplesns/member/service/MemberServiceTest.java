package com.example.samplesns.member.service;

import com.example.samplesns.common.service.port.JwtManager;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.member.dto.LoginRequest;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.mock.FakeJwtManager;
import com.example.samplesns.mock.FakePasswordEncoder;
import com.example.samplesns.mock.TestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberServiceTest {

    @Test
    public void RegisterRequest로_회원가입을_할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().passwordEncoder(new FakePasswordEncoder("encode")).build();

        RegisterRequest request = new RegisterRequest();
        request.setEmail("kwg0527@naver.com");
        request.setPassword1("1q2w3e4r!");
        request.setPassword2("1q2w3e4r!");
        request.setNickname("19");
        request.setBirthday(LocalDate.of(1800, 10, 10));

        // when
        Member member = testContainer.memberService.register(request);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getEmail()).isEqualTo("kwg0527@naver.com");
        assertThat(member.getNickname()).isEqualTo("19");
        assertThat(member.getBirthday()).isEqualTo(LocalDate.of(1800, 10, 10));
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    public void 이미_회원가입한_이메일로_회원가입을_시도하면_에러가_발생한다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        RegisterRequest request = new RegisterRequest();
        request.setEmail("kwg2358@gmail.com");
        request.setPassword1("1q2w3e4r!");
        request.setPassword2("1q2w3e4r!");
        request.setNickname("19");
        request.setBirthday(LocalDate.of(1800, 10, 10));

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.register(request))
                .isInstanceOf(MemberException.class);

    }

//    @Test
//    public void 회원가입시_비밀번호와_비밀번호확인이_틀리면_에러가_발생한다() {
//        // given
//        TestContainer testContainer = TestContainer.builder().build();
//
//        RegisterRequest request = new RegisterRequest();
//        request.setEmail("kwg0527@naver.com");
//        request.setPassword1("1q2w3e4r!");
//        request.setPassword2("1q2w3e4r!!@$@!");
//        request.setNickname("19");
//        request.setBirthday(LocalDate.of(1800, 10, 10));
//
//        // when
//        // then
//        assertThatThrownBy(() -> testContainer.memberService.register(request))
//                .isInstanceOf(MemberException.class);
//    }

    @Test
    public void PENDING_상태인_회원은_유효한_인증코드로_ACTIVE_상태로_변경시킬_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        // when
        testContainer.memberService.verifyEmail(1L, "1q2w3e4r");

        // then
        Member member = testContainer.memberRepository.findById(1L).orElseThrow();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    public void PENDING_상태인_회원은_유효하지_않은_인증코드로_인증을_시도하면_에러가_발생한다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        // when
        // then
        assertThatThrownBy(() -> testContainer.memberService.verifyEmail(1L, "1q2w3e4r5t6y7u8i9o"))
                .isInstanceOf(MemberException.class);

    }

    @Test
    public void 유효한_이메일과_비밀번호로_로그인할_수_있고_Jwt토큰을_반환한다() {
        // given
        FakePasswordEncoder passwordEncoder = new FakePasswordEncoder("encode");

        TestContainer testContainer = TestContainer.builder()
                .passwordEncoder(passwordEncoder)
                .jwtManager(new FakeJwtManager("jwt"))
                .build();

        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password(passwordEncoder.encode("1q2w3e4r!@#$"))
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("kwg2358@gmail.com");
        loginRequest.setPassword("1q2w3e4r!@#$");

        // when
        HttpHeaders result = testContainer.memberService.login(loginRequest);

        // then
        assertThat(result.get(JwtManager.HEADER_STRING).get(0)).isEqualTo("Bearer kwg2358@gmail.comjwt");
    }

    @Test
    public void 유효하지_않은_이메일이면_로그인에_실패한다() {
        // given
        TestContainer testContainer = TestContainer.builder().jwtManager(new FakeJwtManager("jwt")).build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("kwg0527@naver.com");
        loginRequest.setPassword("1q2w3e4r!@#$");

        // when
        // then
        assertThatThrownBy(() ->
                testContainer.memberService.login(loginRequest)
        ).isInstanceOf(MemberException.class);
    }

    @Test
    public void 비밀번호가_틀리면_로그인에_실패한다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .passwordEncoder(new FakePasswordEncoder("encode"))
                .jwtManager(new FakeJwtManager("jwt"))
                .build();
        testContainer.memberRepository.save(Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.PENDING)
                .build());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("kwg2358@gmail.com");
        loginRequest.setPassword("ㅁㄴㄹㅁㄴㅇㄴㅎ");

        // when
        // then
        assertThatThrownBy(() ->
                testContainer.memberService.login(loginRequest)
        ).isInstanceOf(MemberException.class);
    }

}