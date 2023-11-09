package com.example.samplesns.post.controller.port;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostService {
    void createPost(Member member, PostCreateRequest request);
    Slice<DailyPostResponse> getDailyPosts(Member member, DailyPostRequest request, Pageable pageable);
    Slice<PostResponse> getMyPosts(Member member, Pageable pageable);
    Slice<PostResponse> getPosts(String email, Pageable pageable);
    void updatePost(long postId, Member member, PostUpdateRequest request);
    void deletePost(long postId, Member member);
}
