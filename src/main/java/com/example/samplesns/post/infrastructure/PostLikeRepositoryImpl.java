package com.example.samplesns.post.infrastructure;

import com.example.samplesns.post.domain.PostLike;
import com.example.samplesns.post.service.port.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;

    @Override
    public PostLike save(PostLike postLike) {
        return postLikeJpaRepository.save(PostLikeEntity.from(postLike)).toModel();
    }

    @Override
    public Long count(long postId) {
        return postLikeJpaRepository.countByPostId(postId);
    }
}
