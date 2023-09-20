package com.example.samplesns.post.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.mock.TestContainer;
import com.example.samplesns.post.dto.PostCreateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class PostServiceTest {

    @Test
    public void 로그인한_회원은_게시글을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        Member member = Member.builder()
                .id(1L)
                .email("kwg2358@gmail.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        PostCreateRequest postCreateRequest = new PostCreateRequest();
        postCreateRequest.setTitle("제목");
        postCreateRequest.setContents("콘텐츠");

        // when
//        Post post = Post.from(member, postCreateRequest.getTitle(), postCreateRequest.getContents());
//        post = testContainer.postRepository.save(post);
        testContainer.postService.createPost(member, postCreateRequest);

        // then
        assertThat(testContainer.postRepository.getById(1L).getMember().getEmail()).isEqualTo("kwg2358@gmail.com");
        assertThat(testContainer.postRepository.getById(1L).getTitle()).isEqualTo("제목");
        assertThat(testContainer.postRepository.getById(1L).getContents()).isEqualTo("콘텐츠");
    }

}