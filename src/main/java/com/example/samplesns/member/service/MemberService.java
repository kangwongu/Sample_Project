package com.example.samplesns.member.service;

import com.example.samplesns.common.util.RandomCodeCreator;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.dto.RegisterRequest;
import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.member.exception.status.MemberStatus;
import com.example.samplesns.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final CertificationService certificationService;
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Member register(RegisterRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new MemberException(MemberStatus.DUPLICATED_MEMBER);
        }

        String password1 = request.getPassword1();
        String password2 = request.getPassword2();

        if (!password1.equals(password2)) {
            throw new MemberException(MemberStatus.NOT_CORRECT_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(password1);
        RandomCodeCreator randomCodeCreator = new RandomCodeCreator();
        String certificationCode = randomCodeCreator.getRandomCode(10);

        Member member = Member.of(request, encodedPassword, certificationCode);
        Member savedMember = memberRepository.save(member);

        certificationService.send(savedMember.getEmail(), savedMember.getId(), savedMember.getCertificationCode());

        return member;
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

}
