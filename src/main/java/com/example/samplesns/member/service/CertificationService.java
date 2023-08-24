package com.example.samplesns.member.service;

import com.example.samplesns.member.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, long userId, String certificationCode) {
        String certificationUrl = generateCertificationUrl(userId, certificationCode);
        String title = "인증 요청";
        String content = "해당 링크를 클릭해 인증을 완료해주세요: " + certificationUrl;

        mailSender.send(email, title, content);
    }

    private String generateCertificationUrl(long userId, String certificationCode) {
        return "http://localhost:8080/members/" + userId + "/verify?certificationCode=" + certificationCode;
    }
}
