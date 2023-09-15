package com.example.samplesns.follow.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class FollowRequest {
    @Email
    @NotBlank
    private String email;
}
