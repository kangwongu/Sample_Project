package com.example.samplesns.post.service;


import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.controller.port.PostLikeService;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.domain.PostLike;
import com.example.samplesns.post.service.port.PostLikeRepository;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    @Override
    public void createLike(Long postId, Member member) {
        Post post = postRepository.getById(postId);
        PostLike postLike = PostLike.of(post, member);
        
        postLikeRepository.save(postLike);
    }
}
