package com.example.samplesns.member.service;

import com.example.samplesns.mock.TestContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class PasswordServiceTest {

    @Test
    public void 입력받은_패스워드를_암호화할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        String password = "password";

        // when
        String encodedPassword = testContainer.passwordService.encode(password);

        // then
        Assertions.assertThat(encodedPassword).isEqualTo("encoded! password");
    }
}