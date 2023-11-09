package com.example.samplesns.post.service;

import com.example.samplesns.follow.service.port.FollowRepository;
import com.example.samplesns.member.domain.Member;
import com.example.samplesns.member.service.port.MemberRepository;
import com.example.samplesns.post.controller.port.PostService;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.*;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import com.example.samplesns.post.service.port.PostLikeRepository;
import com.example.samplesns.post.service.port.PostRepository;
import com.example.samplesns.timeline.controller.port.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final TimelineService timelineService;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    @Override
    public void createPost(Member member, PostCreateRequest request) {
        Post post = Post.from(member, request.getTitle(), request.getContents());
        post = postRepository.save(post);
        List<Long> followerIds = followRepository.findAllByToMemberId(member.getId())
                .stream()
                .map(f -> f.getFromMember().getId())
                .collect(Collectors.toList());

        timelineService.deliveryTimeline(post.getId(), followerIds);
    }

    @Override
    public Slice<DailyPostResponse> getDailyPosts(Member member, DailyPostRequest request, Pageable pageable) {
        return postRepository.groupByCreateDate(member.getId(), Date.valueOf(request.getFirstDate()), Date.valueOf(request.getLastDate()), pageable);
    }

    @Override
    public Slice<PostResponse> getMyPosts(Member member, Pageable pageable) {
        return postRepository.getMemberPosts(member.getId(), pageable).map(p -> PostResponse.of(p, postLikeRepository.count(p.getId())));
    }

    @Override
    public Slice<PostResponse> getPosts(String email, Pageable pageable) {
        Member findMember = memberRepository.getByEmail(email);

        return postRepository.getMemberPosts(findMember.getId(), pageable).map(p -> PostResponse.of(p, postLikeRepository.count(p.getId())));
    }

    @Transactional
    @Override
    public void updatePost(long postId, Member member, PostUpdateRequest request) {
        Post findPost = postRepository.getById(postId);

        if (!findPost.isValid(member.getId())) {
            throw new PostException(PostStatus.NOT_VALID_PERMISSION);
        }

        Post updatePost = findPost.update(request.getTitle(), request.getContents());
        postRepository.save(updatePost);
    }

    @Transactional
    @Override
    public void deletePost(long postId, Member member) {
        Post findPost = postRepository.getById(postId);

        if (!findPost.isValid(member.getId())) {
            throw new PostException(PostStatus.NOT_VALID_PERMISSION);
        }

        Post deletePost = findPost.delete();
        postRepository.save(deletePost);
    }

//    @Transactional
//    public void likePost(Long postId) {
//        Post post = postRepository.getById(postId);
//        post = post.incrementLikeCount();
//        postRepository.save(post);
//    }
}
