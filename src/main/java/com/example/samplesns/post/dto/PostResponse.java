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
    private LocalDateTime createDate;

    public static PostResponse from(Post p) {
        PostResponse response = new PostResponse();
        response.setTitle(p.getTitle());
        response.setContents(p.getContents());
        response.setEmail(p.getMember().getEmail());
        response.setNickname(p.getMember().getNickname());
        response.setCreateDate(p.getCreateDate());

        return response;
    }
}
