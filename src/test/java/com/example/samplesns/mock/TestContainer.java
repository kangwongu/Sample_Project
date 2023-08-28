package com.example.samplesns.mock;

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
    public MyPasswordEncoder myPasswordEncoder;
    public MailSender mailSender;

    @Builder
    public TestContainer() {
        this.mailSender = new FakeMailSender();
        this.myPasswordEncoder = new FakePasswordEncoder();
        this.memberRepository = new FakeMemberRepository();
        this.certificationService = new CertificationService(mailSender);
        this.passwordService = new PasswordService(myPasswordEncoder);
        this.memberService = new MemberService(passwordService, certificationService, memberRepository);
    }

}
