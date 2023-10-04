package com.example.samplesns.post.domain;

import com.example.samplesns.member.domain.Member;
import com.example.samplesns.post.exception.PostException;
import com.example.samplesns.post.exception.status.PostStatus;
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
    private final Boolean isDelete;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;

    @Builder
    public Post(Long id, Member member, String title, String contents, Boolean isDelete, LocalDateTime createDate, LocalDateTime modifyDate) {
        this.id = id;
        this.member = Objects.requireNonNull(member);
        this.title = Objects.requireNonNull(title);
        this.contents = Objects.requireNonNull(contents);
        this.isDelete = Objects.requireNonNull(isDelete);
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    public static Post from(Member fromMember, String title, String contents) {
        return Post.builder()
                .member(fromMember)
                .title(title)
                .contents(contents)
                .isDelete(false)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
    }

    public boolean isValid(long memberId) {
        return member.getId().equals(memberId);
    }

    public Post update(String title, String contents) {
        return Post.builder()
                .id(id)
                .member(member)
                .title(title)
                .contents(contents)
                .isDelete(isDelete)
                .createDate(getCreateDate())
                .modifyDate(getModifyDate())
                .build();
    }

    public Post delete() {
        if (isDelete) {
            throw new PostException(PostStatus.ALREADY_DELETED_STATE);
        }

        return Post.builder()
                .id(id)
                .member(member)
                .title(title)
                .contents(contents)
                .isDelete(true)
                .createDate(getCreateDate())
                .modifyDate(getModifyDate())
                .build();
    }
}
