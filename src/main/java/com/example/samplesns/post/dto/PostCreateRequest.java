package com.example.samplesns.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 200)
    private String contents;
}
