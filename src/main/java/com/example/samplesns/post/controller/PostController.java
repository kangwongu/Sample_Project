package com.example.samplesns.post.controller;

import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.post.dto.PostCreateRequest;
import com.example.samplesns.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateRequest request,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(userDetails.getMember(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
