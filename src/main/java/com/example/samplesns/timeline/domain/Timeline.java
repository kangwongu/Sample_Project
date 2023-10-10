package com.example.samplesns.timeline.domain;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Timeline {
    private final Long id;
    private final Member member;
    private final Post post;
    private final LocalDateTime createDate;

    @Builder
    public Timeline(Long id, Member member, Post post, LocalDateTime createDate) {
        this.id = id;
        this.member = Objects.requireNonNull(member);
        this.post = Objects.requireNonNull(post);
        this.createDate = createDate;
    }


    public static Timeline of(Member member, Post post) {
        return Timeline.builder()
                .member(member)
                .post(post)
                .build();
    }
}
