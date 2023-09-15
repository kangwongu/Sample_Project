package com.example.samplesns.mock;

import com.example.samplesns.common.service.port.JwtManager;
import com.example.samplesns.follow.service.FollowService;
import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.service.CertificationService;
import com.example.samplesns.member.service.MemberService;
import com.example.samplesns.member.service.PasswordService;
import com.example.samplesns.member.service.port.MailSender;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.member.service.port.MyPasswordEncoder;
import lombok.Builder;

public class TestContainer {

    public MemberService memberService;
    public PasswordService passwordService;
    public CertificationService certificationService;
    public MemberRepository memberRepository;
    public MailSender mailSender;
    public FollowService followService;
    public FollowRepository followRepository;

    @Builder
    public TestContainer(MyPasswordEncoder passwordEncoder, JwtManager jwtManager) {
        this.mailSender = new FakeMailSender();
        this.memberRepository = new FakeMemberRepository();
        this.certificationService = new CertificationService(mailSender);
        this.passwordService = new PasswordService(passwordEncoder);
        this.memberService = new MemberService(passwordService, certificationService, memberRepository, jwtManager);
        this.followRepository = new FakeFollowRepository();
        this.followService = new FollowService(memberRepository, followRepository);
    }

}
