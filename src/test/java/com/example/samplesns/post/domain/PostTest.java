package com.example.samplesns.post.domain;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    public void Member와_제목_콘텐츠로_Post_객체를_생성할_수_있다() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();
        String title = "제목";
        String contents = "콘텐츠";

        // when
        Post post = Post.from(member, title, contents);

        // then
        assertThat(post.getMember().getEmail()).isEqualTo("kwg2358@gmail.com");
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContents()).isEqualTo("콘텐츠");
    }
}