package com.example.samplesns.post.controller;

import com.example.samplesns.common.security.userdetails.UserDetailsImpl;
import com.example.samplesns.post.controller.port.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postLikeService.createLike(postId, userDetails.getMember());

        return ResponseEntity.ok().build();
    }
}
