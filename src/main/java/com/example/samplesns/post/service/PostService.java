package com.example.samplesns.post.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostRequest;
import com.example.samplesns.post.dto.DailyPostResponse;
import com.example.samplesns.post.dto.PostCreateRequest;
import com.example.samplesns.post.dto.PostResponse;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createPost(Member member, PostCreateRequest request) {
        Post post = Post.from(member, request.getTitle(), request.getContents());
        postRepository.save(post);
    }

    public Slice<DailyPostResponse> getDailyPosts(Member member, DailyPostRequest request, Pageable pageable) {
        return postRepository.groupByCreateDate(member.getId(), request.getFirstDate(), request.getLastDate(), pageable);
    }

    public Slice<PostResponse> getMyPosts(Member member, Pageable pageable) {
        return postRepository.getMemberPosts(member.getId(), pageable).map(p -> PostResponse.from(p));
    }

    public Slice<PostResponse> getPosts(String email, Pageable pageable) {
        Member findMember = memberRepository.getByEmail(email);

        return postRepository.getMemberPosts(findMember.getId(), pageable).map(p -> PostResponse.from(p));
    }
}
