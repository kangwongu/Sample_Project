package com.example.samplesns.post.controller.port;

import com.example.samplesns.member.domain.Member;

public interface PostLikeService {
    void createLike(Long postId, Member member);
}
