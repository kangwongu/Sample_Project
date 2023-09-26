package com.example.samplesns.post.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.domain.MemberStatus;
import com.example.samplesns.mock.TestContainer;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.PostCreateRequest;
import com.example.samplesns.post.dto.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

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

//    @Test
//    public void 로그인한_회원은_일자별로_게시글을_조회할_수_있다() {
//        // given
//        TestContainer testContainer = TestContainer.builder().build();
//
//        Member member = Member.builder()
//                .id(1L)
//                .email("kwg2358@gmail.com")
//                .password("1q2w3e4r!@#$")
//                .nickname("9")
//                .birthday(LocalDate.of(1800, 11, 11))
//                .certificationCode("1q2w3e4r")
//                .status(MemberStatus.ACTIVE)
//                .build();
//        testContainer.memberRepository.save(member);
//
//        Post post = Post.from(member, "제목", "콘텐츠");
//        testContainer.postRepository.save(post);
//
//        // when
//        List<Post> posts = testContainer.postRepository.groupByCreateDate(member.getId(), LocalDate.of(2023, 9, 24), LocalDate.of(2023, 9, 28));
//
//        // then
//        assertThat(posts.size()).isEqualTo(1);
//    }

    @Test
    public void 로그인한_회원은_자신이_작성한_게시글_목록을_조회할_수_있다() throws InterruptedException {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        Member member = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();
        testContainer.memberRepository.save(member);

        Post post1 = Post.from(member, "제목", "콘텐츠");
        Post post2 = Post.from(member, "제목2", "콘텐츠2");
        testContainer.postRepository.save(post1);
        testContainer.postRepository.save(post2);

        // when
        Slice<PostResponse> posts = testContainer.postService.getMyPosts(member, PageRequest.of(0, 5));

        // then
        assertThat(posts.getContent().size()).isEqualTo(2);

    }

    @Test
    public void 로그인한_회원이_자신이_작성한_게시글을_조회할_때_최신순으로_정렬해서_조회한다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        Member member = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();
        testContainer.memberRepository.save(member);

        Post post1 = Post.from(member, "제목", "콘텐츠");
        Post post2 = Post.from(member, "제목2", "콘텐츠2");
        testContainer.postRepository.save(post1);
        testContainer.postRepository.save(post2);

        // when
        Slice<PostResponse> posts = testContainer.postService.getMyPosts(member, PageRequest.of(0, 5));

        // then
        assertThat(posts.getContent().get(0).getTitle()).isEqualTo("제목2");
        assertThat(posts.getContent().get(0).getContents()).isEqualTo("콘텐츠2");
        assertThat(posts.getContent().get(0).getEmail()).isEqualTo("kwg0527@naver.com");
        assertThat(posts.getContent().get(0).getNickname()).isEqualTo("19");
        assertThat(posts.getContent().get(0).getCreateDate()).isEqualTo(post2.getCreateDate());
        assertThat(posts.getContent().get(1).getTitle()).isEqualTo("제목");
        assertThat(posts.getContent().get(1).getContents()).isEqualTo("콘텐츠");
        assertThat(posts.getContent().get(1).getEmail()).isEqualTo("kwg0527@naver.com");
        assertThat(posts.getContent().get(1).getNickname()).isEqualTo("19");
        assertThat(posts.getContent().get(1).getCreateDate()).isEqualTo(post1.getCreateDate());
    }

    @Test
    public void 로그인한_회원은_특정_회원이_작성한_게시글_목록을_조회할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        Member loginMember = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        Member writer = Member.builder()
                .id(2L)
                .email("kwg2358@gmial.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();
        testContainer.memberRepository.save(loginMember);
        testContainer.memberRepository.save(writer);

        Post post1 = Post.from(writer, "제목", "콘텐츠");
        Post post2 = Post.from(writer, "제목2", "콘텐츠2");
        testContainer.postRepository.save(post1);
        testContainer.postRepository.save(post2);

        // when
        Slice<PostResponse> posts = testContainer.postService.getPosts(writer.getEmail(), PageRequest.of(0, 5));

        // then
        assertThat(posts.getContent().size()).isEqualTo(2);
    }

    @Test
    public void 특정_회원의_게시글을_조회할_때_최신순으로_정렬해서_조회한다() {
        // given
        TestContainer testContainer = TestContainer.builder().build();

        Member loginMember = Member.builder()
                .id(1L)
                .email("kwg0527@naver.com")
                .password("1q2w3e4r!@#$")
                .nickname("19")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();

        Member writer = Member.builder()
                .id(2L)
                .email("kwg2358@gmial.com")
                .password("1q2w3e4r!@#$")
                .nickname("9")
                .birthday(LocalDate.of(1800, 11, 11))
                .certificationCode("1q2w3e4r")
                .status(MemberStatus.ACTIVE)
                .build();
        testContainer.memberRepository.save(loginMember);
        testContainer.memberRepository.save(writer);

        Post post1 = Post.from(writer, "제목", "콘텐츠");
        Post post2 = Post.from(writer, "제목2", "콘텐츠2");
        testContainer.postRepository.save(post1);
        testContainer.postRepository.save(post2);

        // when
        Slice<PostResponse> posts = testContainer.postService.getPosts(writer.getEmail(), PageRequest.of(0, 5));

        // then
        assertThat(posts.getContent().get(0).getTitle()).isEqualTo("제목2");
        assertThat(posts.getContent().get(0).getContents()).isEqualTo("콘텐츠2");
        assertThat(posts.getContent().get(0).getEmail()).isEqualTo("kwg2358@gmial.com");
        assertThat(posts.getContent().get(0).getNickname()).isEqualTo("9");
        assertThat(posts.getContent().get(0).getCreateDate()).isEqualTo(post2.getCreateDate());
        assertThat(posts.getContent().get(1).getTitle()).isEqualTo("제목");
        assertThat(posts.getContent().get(1).getContents()).isEqualTo("콘텐츠");
        assertThat(posts.getContent().get(1).getEmail()).isEqualTo("kwg2358@gmial.com");
        assertThat(posts.getContent().get(1).getNickname()).isEqualTo("9");
        assertThat(posts.getContent().get(1).getCreateDate()).isEqualTo(post1.getCreateDate());
    }
}