package com.example.samplesns.post.exception;

import com.example.samplesns.post.exception.status.PostStatus;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final PostStatus status;

    public PostException(PostStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
