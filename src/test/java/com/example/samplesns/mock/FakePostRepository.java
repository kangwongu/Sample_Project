package com.example.samplesns.mock;

import com.example.samplesns.post.domain.Post;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
import com.example.samplesns.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Post> data = new ArrayList<>();

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .member(post.getMember())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .createDate(post.getCreateDate())
                    .modifyDate(post.getModifyDate())
                    .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(d -> Objects.equals(d.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }

    @Override
    public Post getById(long postId) {
        return data.stream().filter(d -> d.getId().equals(postId)).findAny()
                .orElseThrow(() -> new PostException(PostStatus.NOT_EXIST_POST));
    }
}