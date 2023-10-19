package com.example.samplesns.post.dto;

import com.example.samplesns.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostResponse {
    private String title;
    private String contents;
    private String email;
    private String nickname;
    private Long likeCount;
    private LocalDateTime createDate;

    public static PostResponse of(Post p, Long count) {
        PostResponse response = new PostResponse();
        response.setTitle(p.getTitle());
        response.setContents(p.getContents());
        response.setEmail(p.getMember().getEmail());
        response.setNickname(p.getMember().getNickname());
        response.setLikeCount(count);
        response.setCreateDate(p.getCreateDate());

        return response;
    }
}
