package com.example.samplesns.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class PostUpdateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String contents;
}
