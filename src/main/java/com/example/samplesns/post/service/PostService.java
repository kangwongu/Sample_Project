package com.example.samplesns.post.service;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.dto.DailyPostRequest;
import com.example.samplesns.post.dto.DailyPostResponse;
import com.example.samplesns.post.dto.PostCreateRequest;
import com.example.samplesns.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void createPost(Member member, PostCreateRequest request) {
        Post post = Post.from(member, request.getTitle(), request.getContents());
        postRepository.save(post);
    }

    public List<DailyPostResponse> getDailyPosts(Member member, DailyPostRequest request) {
        return postRepository.groupByCreateDate(member.getId(), request.getFirstDate(), request.getLastDate());
    }
}
