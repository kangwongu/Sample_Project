package com.example.samplesns.post.service.port;

import com.example.samplesns.post.domain.PostLike;

public interface PostLikeRepository {

    PostLike save(PostLike postLike);

    Long count(long postId);
}
