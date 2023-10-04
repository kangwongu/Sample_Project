package com.example.samplesns.post.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.*;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
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

    @Transactional
    public void updatePost(long postId, Member member, PostUpdateRequest request) {
        Post findPost = postRepository.getById(postId);

        if (!findPost.isValid(member.getId())) {
            throw new PostException(PostStatus.NOT_VALID_PERMISSION);
        }

        Post updatePost = findPost.update(request.getTitle(), request.getContents());
        postRepository.save(updatePost);
    }

    @Transactional
    public void deletePost(long postId, Member member) {
        Post findPost = postRepository.getById(postId);

        if (!findPost.isValid(member.getId())) {
            throw new PostException(PostStatus.NOT_VALID_PERMISSION);
        }

        Post deletePost = findPost.delete();
        postRepository.save(deletePost);
    }
}
