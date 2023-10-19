package com.example.samplesns.mock;

import com.example.samplesns.post.domain.PostLike;
import com.example.samplesns.post.service.port.PostLikeRepository;

public class FakePostLikeRepository implements PostLikeRepository {
    @Override
    public PostLike save(PostLike postLike) {
        return null;
    }

    @Override
    public Long count(long postId) {
        return null;
    }
}
