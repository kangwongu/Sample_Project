package com.example.samplesns.member.service;

import com.example.samplesns.common.jwt.JwtManager;
import com.example.samplesns.common.util.RandomCodeCreator;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.LoginRequest;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordService passwordService;
    private final CertificationService certificationService;
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    // 회원가입
    @Transactional
    public Member register(RegisterRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new MemberException(MemberStatus.DUPLICATED_MEMBER);
        }

        String encodedPassword = passwordService.encode(request.getPassword1(), request.getPassword2());
        RandomCodeCreator randomCodeCreator = new RandomCodeCreator();
        String certificationCode = randomCodeCreator.getRandomCode(10);

        Member member = Member.of(request, encodedPassword, certificationCode);
        Member savedMember = memberRepository.save(member);

        certificationService.send(savedMember.getEmail(), savedMember.getId(), savedMember.getCertificationCode());

        return savedMember;
    }

    // 인증
    @Transactional
    public void verifyEmail(long memberId, String certificationCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberStatus.NOT_EXIST_MEMBER));

        member = member.verify(certificationCode);
        /* 도메인 모델과 영속성 객체 분리 -> JPA의 변경감지 사용 x */
        memberRepository.save(member);
    }

    // 로그인
    public HttpHeaders login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberException(MemberStatus.NOT_EXIST_MEMBER));
        passwordService.matches(request.getPassword(), member.getPassword());

        String token = jwtManager.generateAccessToken(member.getEmail());
        log.info("Jwt Token : {}", token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtManager.HEADER_STRING, token);

        return httpHeaders;
    }
}
