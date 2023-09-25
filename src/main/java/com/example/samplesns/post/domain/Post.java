package com.example.samplesns.post.domain;

import com.example.samplesns.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;
    private final Member member;
    private final String title;
    private final String contents;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;

    @Builder
    public Post(Long id, Member member, String title, String contents, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.member = Objects.requireNonNull(member);
        this.title = Objects.requireNonNull(title);
        this.contents = Objects.requireNonNull(contents);
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public static Post from(Member fromMember, String title, String contents) {
        return Post.builder()
                .member(fromMember)
                .title(title)
                .contents(contents)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
    }
}
