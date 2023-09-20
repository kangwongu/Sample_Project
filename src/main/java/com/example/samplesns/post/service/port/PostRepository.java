package com.example.samplesns.post.service.port;

import com.example.samplesns.post.domain.Post;

public interface PostRepository {

    Post save(Post post);

    Post getById(long postId);
}
