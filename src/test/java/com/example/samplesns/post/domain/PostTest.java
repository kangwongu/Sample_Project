package com.example.samplesns.post.domain;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.post.exception.PostException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    public void 제목_콘텐츠로_Post_객체를_수정할_수_있다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .member(Member.builder()
                        .id(1L)
                        .email("kwg2358@gmail.com")
                        .password("1q2w3e4r!@#$")
                        .nickname("9")
                        .birthday(LocalDate.of(1800, 11, 11))
                        .certificationCode("1q2w3e4r")
                        .status(MemberStatus.ACTIVE)
                        .build())
                .title("제목이에요")
                .contents("본문이에요")
                .isDelete(false)
                .build();

        // when
        Post updatePost = post.update("제목", "본문");

        // then
        assertThat(updatePost.getTitle()).isEqualTo("제목");
        assertThat(updatePost.getContents()).isEqualTo("본문");
    }

    @Test
    public void 게시글을_삭제_처리할_수_있다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .member(Member.builder()
                        .id(1L)
                        .email("kwg2358@gmail.com")
                        .password("1q2w3e4r!@#$")
                        .nickname("9")
                        .birthday(LocalDate.of(1800, 11, 11))
                        .certificationCode("1q2w3e4r")
                        .status(MemberStatus.ACTIVE)
                        .build())
                .title("제목이에요")
                .contents("본문이에요")
                .isDelete(false)
                .build();

        // when
        Post deletePost = post.delete();

        // then
        assertThat(deletePost.getIsDelete()).isTrue();
    }

    @Test
    public void 이미_삭제된_게시글을_삭제_처리할_수_없다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .member(Member.builder()
                        .id(1L)
                        .email("kwg2358@gmail.com")
                        .password("1q2w3e4r!@#$")
                        .nickname("9")
                        .birthday(LocalDate.of(1800, 11, 11))
                        .certificationCode("1q2w3e4r")
                        .status(MemberStatus.ACTIVE)
                        .build())
                .title("제목이에요")
                .contents("본문이에요")
                .isDelete(true)
                .build();

        // then
        // when
        assertThatThrownBy(() -> post.delete())
                .isInstanceOf(PostException.class);
    }
}