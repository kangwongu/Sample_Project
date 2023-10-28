package com.example.samplesns.post.domain;

import com.example.samplesns.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostLike {

    private final Long id;
    private final Post post;
    private final Member member;
    private final LocalDateTime createDate;

    @Builder
    public PostLike(Long id, Post post, Member member, LocalDateTime createDate) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.createDate = createDate;
    }

    public static PostLike of(Post post, Member member) {
        return PostLike.builder()
                .post(post)
                .member(member)
                .createDate(LocalDateTime.now())
                .build();
    }
}
