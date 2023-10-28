package com.example.samplesns.member.service;


import com.example.samplesns.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CertificationServiceTest {

    @Test
    public void 인증_메일이_제대로_전송되는지_테스트할_수_있다() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when
        certificationService.send("kwg0527@naver.com", 1, "1q2w3e4r!@#");

        // then
        assertThat(fakeMailSender.email).isEqualTo("kwg0527@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("인증 요청");
        assertThat(fakeMailSender.content).isEqualTo("해당 링크를 클릭해 인증을 완료해주세요: http://localhost:8080/members/1/verify?certificationCode=1q2w3e4r!@#");

    }
}