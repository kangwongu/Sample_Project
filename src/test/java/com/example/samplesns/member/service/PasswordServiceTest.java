package com.example.samplesns.member.service;

import com.example.samplesns.member.exception.MemberException;
import com.example.samplesns.mock.TestContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class PasswordServiceTest {

    @Test
    public void 입력받은_패스워드를_암호화할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        String password1 = "password";
        String password2 = "password";

        // when
        String encodedPassword = testContainer.passwordService.encode(password1, password2);

        // then
        assertThat(encodedPassword).isEqualTo("encoded! password");
    }

    @Test
    public void 입력받은_패스워드가_다르면_에러가_발생한다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();
        String password1 = "password";
        String password2 = "password123";

        // when
        // then
        assertThatThrownBy(() -> testContainer.passwordService.encode(password1, password2))
                .isInstanceOf(MemberException.class);

    }

}